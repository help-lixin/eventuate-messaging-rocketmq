package help.lixin.eventuate.messaging.rocketmq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EventuateRocketMQProducer {

    private final DefaultMQProducer producer;

    private List<IDefaultMQProducerCustomizer> defaultMQProducerCustomizers = new ArrayList<>(0);

    private EventuateRocketMQPartitioner eventuateRocketMQPartitioner = new EventuateRocketMQPartitioner();

    public EventuateRocketMQProducer(String namesrvAddrs, //
                                     EventuateRocketMQProducerConfigurationProperties eventuateRocketMQProducerConfigurationProperties) throws Exception {
        this(namesrvAddrs, eventuateRocketMQProducerConfigurationProperties, null);
    }


    public EventuateRocketMQProducer(String namesrvAddrs, //
                                     EventuateRocketMQProducerConfigurationProperties eventuateRocketMQProducerConfigurationProperties, //
                                     List<IDefaultMQProducerCustomizer> defaultMQProducerCustomizers) throws Exception {
        if (null != defaultMQProducerCustomizers && !defaultMQProducerCustomizers.isEmpty()) {
            this.defaultMQProducerCustomizers.addAll(defaultMQProducerCustomizers);
        }


        // 创建Producer
        producer = new DefaultMQProducer("eventuateRocketMQProducerGroup");
        producer.setNamesrvAddr(namesrvAddrs);
        producer.setRetryTimesWhenSendFailed(3);

        // 扩展事项,交给:IDefaultMQProducerCustomizer
        this.defaultMQProducerCustomizers.forEach((defaultMQProducerCustomizer) -> {
            defaultMQProducerCustomizer.customizer(producer, eventuateRocketMQProducerConfigurationProperties);
        });
        producer.start();
    }


    public CompletableFuture<?> send(String topic, String key, String body) {
        return send(topic, key, body.getBytes());
    }


    public CompletableFuture<?> send(String topic, String key, byte[] body) {
        Message message = new Message(topic, key, body);
        return send(message);
    }

    protected CompletableFuture<?> send(Message message) {
        CompletableFuture<Object> result = new CompletableFuture<>();
        try {
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    result.complete(sendResult);
                }

                @Override
                public void onException(Throwable e) {
                    result.completeExceptionally(e);
                }
            });
        } catch (Exception e) {
            result.completeExceptionally(e);
        }
        return result;
    }


    public int partitionFor(String topic, String key) {
        String keyBytes = String.format("%s%s", topic, key);
        return eventuateRocketMQPartitioner.partition(topic, keyBytes.getBytes(), partitionsFor(topic));
    }

    public List<MessageQueue> partitionsFor(String topic) {
        try {
            return producer.fetchPublishMessageQueues(topic);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        producer.shutdown();
    }
}
