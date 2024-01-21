package help.lixin.eventuate.messaging.rocketmq.spring.common;

import help.lixin.eventuate.messaging.rocketmq.common.EventuateRocketMQConfigurationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventuateRocketMQPropertiesConfiguration {


    @Bean
    public EventuateRocketMQConfigurationProperties //
    eventuateRocketMQConfigurationProperties(@Value("${eventuate.local.rocket.mq.nameserver.address}") String nameServerAddress) {
        return new EventuateRocketMQConfigurationProperties(nameServerAddress);
    }
}
