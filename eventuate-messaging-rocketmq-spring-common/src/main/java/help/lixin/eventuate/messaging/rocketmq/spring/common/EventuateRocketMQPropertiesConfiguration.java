package help.lixin.eventuate.messaging.rocketmq.spring.common;

import help.lixin.eventuate.messaging.rocketmq.common.EventuateRocketMQConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

@Configuration
public class EventuateRocketMQPropertiesConfiguration {

    @Bean
    public EventuateRocketMQConfigurationProperties eventuateRocketMQConfigurationProperties(Environment environment) {
        String nameServerAddress = null;
        nameServerAddress = environment.getProperty("eventuatelocal.rocket.mq.nameserver.address", String.class);
        if (null == nameServerAddress) {
            nameServerAddress = environment.getProperty("eventuate.local.rocket.mq.nameserver.address", String.class);
        }
        Assert.notNull(nameServerAddress, "请配置nameserver地址.");
        return new EventuateRocketMQConfigurationProperties(nameServerAddress);
    }
}
