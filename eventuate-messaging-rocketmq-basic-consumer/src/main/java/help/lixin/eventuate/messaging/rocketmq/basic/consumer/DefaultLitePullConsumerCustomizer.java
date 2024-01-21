package help.lixin.eventuate.messaging.rocketmq.basic.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MQPushConsumer;

import java.util.Properties;

public class DefaultLitePullConsumerCustomizer implements IMQPushConsumerCustomizer {
    @Override
    public void customizer(MQPushConsumer consumer, Properties properties) {
        if (consumer instanceof MQPushConsumer) {
            DefaultMQPushConsumer defaultLitePullConsumer = (DefaultMQPushConsumer) consumer;
            if (properties.containsKey(ConsumerPropertiesFactory.NAME_SRV_ADDR_KEY)) {
                defaultLitePullConsumer.setNamesrvAddr(properties.getProperty(ConsumerPropertiesFactory.NAME_SRV_ADDR_KEY));
            }
            if (properties.containsKey(ConsumerPropertiesFactory.CONSUMER_GROUP_KEY)) {
                defaultLitePullConsumer.setConsumerGroup(properties.getProperty(ConsumerPropertiesFactory.CONSUMER_GROUP_KEY));
            }
        }
    }
}
