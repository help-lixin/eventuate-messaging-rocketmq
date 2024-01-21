package help.lixin.eventuate.messaging.rocketmq.spring.producer;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties("eventuate.local.rocket.mq.producer")
public class EventuateRocketMQProducerSpringConfigurationProperties {
  Map<String, String> properties = new HashMap<>();

  public Map<String, String> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
  }

  public static EventuateRocketMQProducerSpringConfigurationProperties empty() {
    return new EventuateRocketMQProducerSpringConfigurationProperties();
  }
}
