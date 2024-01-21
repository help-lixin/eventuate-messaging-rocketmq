package help.lixin.eventuate.messaging.rocketmq.basic.consumer;

import org.apache.rocketmq.client.consumer.MQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.topic.TopicValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DefaultRocketMQMessageConsumer implements RocketMQMessageConsumer {

  private final MQPushConsumer delegateConsumer;

  private final MQProducer delegateProducer;


  public DefaultRocketMQMessageConsumer(MQProducer delegateProducer, //
                                        MQPushConsumer targetConsumer) {
    this.delegateConsumer = targetConsumer;
    this.delegateProducer = delegateProducer;
  }

  @Override
  public void createTopic(String topic) {
    try {
      delegateProducer.createTopic(TopicValidator.AUTO_CREATE_TOPIC_KEY_TOPIC, topic, 4);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void subscribe(String topic) {
    try {
      delegateConsumer.subscribe(topic, "*");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<MessageQueue> partitionsFor(String topic) {
    List<MessageQueue> queues = new ArrayList<>(0);
    try {
      List<MessageQueue> messageQueues = delegateProducer.fetchPublishMessageQueues(topic);
      queues.addAll(messageQueues);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return queues;
  }

  @Override
  public void register(Consumer<List<MessageExt>> consumer) {
    delegateConsumer.registerMessageListener(new MessageListenerConcurrently() {
      @Override
      public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        consumer.accept(msgs);
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
      }
    });
  }

  public void start() {
    try {
      delegateProducer.start();
      delegateConsumer.start();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void close() {
    delegateConsumer.shutdown();
  }
}
