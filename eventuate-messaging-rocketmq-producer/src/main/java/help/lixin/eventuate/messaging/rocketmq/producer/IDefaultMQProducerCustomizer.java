package help.lixin.eventuate.messaging.rocketmq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;

public interface IDefaultMQProducerCustomizer {
    void customizer(DefaultMQProducer producer, EventuateRocketMQProducerConfigurationProperties eventuateRocketMQProducerConfigurationProperties);
}
