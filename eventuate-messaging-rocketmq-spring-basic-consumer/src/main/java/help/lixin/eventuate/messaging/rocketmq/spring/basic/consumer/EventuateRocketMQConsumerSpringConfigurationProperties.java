package help.lixin.eventuate.messaging.rocketmq.spring.basic.consumer;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties("eventuate.local.rocket.mq.consumer")
public class EventuateRocketMQConsumerSpringConfigurationProperties {
  Map<String, String> properties = new HashMap<>();

  private long pollTimeout = 100;

  public long getPollTimeout() {
    return pollTimeout;
  }

  public void setPollTimeout(long pollTimeout) {
    this.pollTimeout = pollTimeout;
  }

  public Map<String, String> getProperties() {
    return properties;
  }
}
