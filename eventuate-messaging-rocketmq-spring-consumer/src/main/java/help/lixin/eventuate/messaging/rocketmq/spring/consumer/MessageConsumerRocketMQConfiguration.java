package help.lixin.eventuate.messaging.rocketmq.spring.consumer;

import help.lixin.eventuate.messaging.rocketmq.basic.consumer.EventuateRocketMQConsumerConfigurationProperties;
import help.lixin.eventuate.messaging.rocketmq.basic.consumer.RocketMQConsumerFactory;
import help.lixin.eventuate.messaging.rocketmq.common.EventuateRocketMQConfigurationProperties;
import help.lixin.eventuate.messaging.rocketmq.consumer.MessageConsumerRocketMQImpl;
import help.lixin.eventuate.messaging.rocketmq.spring.basic.consumer.EventuateRocketMQConsumerSpringConfigurationPropertiesConfiguration;
import help.lixin.eventuate.messaging.rocketmq.spring.common.EventuateRocketMQPropertiesConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({EventuateRocketMQPropertiesConfiguration.class, EventuateRocketMQConsumerSpringConfigurationPropertiesConfiguration.class})


public class MessageConsumerRocketMQConfiguration {


  @Bean
  public MessageConsumerRocketMQImpl messageConsumerKafka(EventuateRocketMQConfigurationProperties props, //
                                                          EventuateRocketMQConsumerConfigurationProperties eventuateRocketMQConsumerConfigurationProperties, //
                                                          RocketMQConsumerFactory kafkaConsumerFactory) {
    return new MessageConsumerRocketMQImpl(props.getNameServerAddress(), eventuateRocketMQConsumerConfigurationProperties, kafkaConsumerFactory);
  }
}
