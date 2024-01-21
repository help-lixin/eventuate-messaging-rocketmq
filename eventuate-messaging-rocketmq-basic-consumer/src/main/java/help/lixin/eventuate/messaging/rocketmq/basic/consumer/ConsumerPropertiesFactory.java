package help.lixin.eventuate.messaging.rocketmq.basic.consumer;

import java.util.Properties;

public class ConsumerPropertiesFactory {

  public static final String NAME_SRV_ADDR_KEY = "namesrvAddr";
  public static final String CONSUMER_GROUP_KEY = "consumerGroup";

  public static Properties makeDefaultConsumerProperties(String namesrvAddr, String subscriberId) {
    Properties consumerProperties = new Properties();
    consumerProperties.put(NAME_SRV_ADDR_KEY, namesrvAddr);
    consumerProperties.put(CONSUMER_GROUP_KEY, subscriberId);
    return consumerProperties;
  }
}
