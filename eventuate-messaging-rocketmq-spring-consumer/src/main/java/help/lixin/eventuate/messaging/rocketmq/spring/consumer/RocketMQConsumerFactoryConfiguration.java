package help.lixin.eventuate.messaging.rocketmq.spring.consumer;

import help.lixin.eventuate.messaging.rocketmq.basic.consumer.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RocketMQConsumerFactoryConfiguration {


  @Bean
  public IMQPushConsumerCustomizer defaultLitePullConsumerCustomizer() {
    return new DefaultLitePullConsumerCustomizer();
  }


  @Bean
  public IMQProducerCustomizer defaultMQProducerCustomizer() {
    return new DefaultMQProducerCustomizer();
  }


  @Bean
  @ConditionalOnMissingBean
  public RocketMQConsumerFactory kafkaConsumerFactory(List<IMQPushConsumerCustomizer> consumerCustomizers, //
                                                      List<IMQProducerCustomizer> producerCustomizers) {
    return new DefaultRocketMQConsumerFactory(producerCustomizers, consumerCustomizers);
  }
}
