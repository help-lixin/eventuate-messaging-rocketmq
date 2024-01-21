package help.lixin.eventuate.messaging.rocketmq.basic.consumer;

import java.util.HashMap;
import java.util.Map;

public class EventuateRocketMQConsumerConfigurationProperties {
  private Map<String, String> properties = new HashMap<>();
  private long pollTimeout;

  public long getPollTimeout() {
    return pollTimeout;
  }

  public void setPollTimeout(long pollTimeout) {
    this.pollTimeout = pollTimeout;
  }

  public Map<String, String> getProperties() {
    return properties;
  }

  public EventuateRocketMQConsumerConfigurationProperties() {
  }

  public EventuateRocketMQConsumerConfigurationProperties(Map<String, String> properties) {
    this.properties = properties;
  }

  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
  }

  public static EventuateRocketMQConsumerConfigurationProperties empty() {
    return new EventuateRocketMQConsumerConfigurationProperties();
  }

}
