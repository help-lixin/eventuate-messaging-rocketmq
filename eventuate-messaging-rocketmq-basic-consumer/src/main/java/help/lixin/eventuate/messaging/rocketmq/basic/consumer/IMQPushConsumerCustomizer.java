package help.lixin.eventuate.messaging.rocketmq.basic.consumer;

import org.apache.rocketmq.client.consumer.LitePullConsumer;
import org.apache.rocketmq.client.consumer.MQPushConsumer;

import java.util.Properties;

public interface IMQPushConsumerCustomizer {
    void customizer(MQPushConsumer consumer, Properties properties);
}
