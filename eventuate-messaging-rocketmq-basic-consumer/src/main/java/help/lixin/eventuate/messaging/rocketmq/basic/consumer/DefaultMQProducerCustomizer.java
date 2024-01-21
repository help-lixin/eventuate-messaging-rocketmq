package help.lixin.eventuate.messaging.rocketmq.basic.consumer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;

import java.util.Properties;

public class DefaultMQProducerCustomizer implements IMQProducerCustomizer {
    @Override
    public void customizer(MQProducer producer, Properties properties) {
        if (producer instanceof DefaultMQProducer) {
            DefaultMQProducer defaultMQProducer = (DefaultMQProducer) producer;
            if (properties.containsKey(ConsumerPropertiesFactory.NAME_SRV_ADDR_KEY)) {
                defaultMQProducer.setNamesrvAddr(properties.getProperty(ConsumerPropertiesFactory.NAME_SRV_ADDR_KEY));
            }
        }
    }
}
