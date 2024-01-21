package help.lixin.eventuate.messaging.rocketmq.basic.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MQPushConsumer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DefaultRocketMQConsumerFactory implements RocketMQConsumerFactory {

  private List<IMQPushConsumerCustomizer> consumerCustomizers = new ArrayList<>(0);

  private List<IMQProducerCustomizer> producerCustomizers = new ArrayList<>(0);


  public DefaultRocketMQConsumerFactory(List<IMQProducerCustomizer> producerCustomizers, //
                                        List<IMQPushConsumerCustomizer> consumerCustomizers) {
    if (null != producerCustomizers) {
      this.producerCustomizers.addAll(producerCustomizers);
    }
    if (null != consumerCustomizers) {
      this.consumerCustomizers.addAll(consumerCustomizers);
    }
  }


  @Override
  public RocketMQMessageConsumer makeConsumer(String subscriptionId, Properties properties) {
    MQPushConsumer targetConsumer = new DefaultMQPushConsumer();
    MQProducer targetProducer = new DefaultMQProducer(subscriptionId);

    producerCustomizers.forEach((customizer) -> customizer.customizer(targetProducer, properties));
    consumerCustomizers.forEach(customizer -> customizer.customizer(targetConsumer, properties));
    DefaultRocketMQMessageConsumer consumer = new DefaultRocketMQMessageConsumer(targetProducer, targetConsumer);
    return consumer;
  }
}
