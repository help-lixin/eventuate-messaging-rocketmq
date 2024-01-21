package help.lixin.eventuate.messaging.rocketmq.producer;

import java.util.HashMap;
import java.util.Map;

public class EventuateRocketMQProducerConfigurationProperties {
    Map<String, String> properties = new HashMap<>();

    public EventuateRocketMQProducerConfigurationProperties() {
    }

    public EventuateRocketMQProducerConfigurationProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public static EventuateRocketMQProducerConfigurationProperties empty() {
        return new EventuateRocketMQProducerConfigurationProperties();
    }
}
