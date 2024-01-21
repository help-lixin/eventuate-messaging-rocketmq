package help.lixin.eventuate.messaging.rocketmq.basic.consumer;

import java.util.Properties;

public interface RocketMQConsumerFactory {

  RocketMQMessageConsumer makeConsumer(String subscriptionId, Properties properties);

}
