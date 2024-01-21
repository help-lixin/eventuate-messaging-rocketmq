package help.lixin.eventuate.messaging.rocketmq.basic.consumer;

import org.apache.rocketmq.client.producer.MQProducer;

import java.util.Properties;

public interface IMQProducerCustomizer {
    void customizer(MQProducer producer, Properties properties);
}
