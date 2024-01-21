package help.lixin.eventuate.tram.consumer.rocketmq;


import help.lixin.eventuate.messaging.rocketmq.consumer.MessageConsumerRocketMQImpl;
import help.lixin.eventuate.messaging.rocketmq.spring.consumer.MessageConsumerRocketMQConfiguration;
import help.lixin.eventuate.messaging.rocketmq.spring.consumer.RocketMQConsumerFactoryConfiguration;
import io.eventuate.tram.consumer.common.MessageConsumerImplementation;
import io.eventuate.tram.spring.consumer.common.TramConsumerCommonConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({TramConsumerCommonConfiguration.class, MessageConsumerRocketMQConfiguration.class, RocketMQConsumerFactoryConfiguration.class})
public class EventuateTramRocketMQMessageConsumerConfiguration {
    @Bean
    public MessageConsumerImplementation messageConsumerImplementation(MessageConsumerRocketMQImpl messageConsumerRocketMQ) {
        return new EventuateTramRocketMQMessageConsumer(messageConsumerRocketMQ);
    }
}
