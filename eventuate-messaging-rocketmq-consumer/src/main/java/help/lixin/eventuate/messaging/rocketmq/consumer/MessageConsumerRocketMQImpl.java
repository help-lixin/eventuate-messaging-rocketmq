package help.lixin.eventuate.messaging.rocketmq.consumer;

import help.lixin.eventuate.messaging.rocketmq.basic.consumer.EventuateRocketMQConsumer;
import help.lixin.eventuate.messaging.rocketmq.basic.consumer.EventuateRocketMQConsumerConfigurationProperties;
import help.lixin.eventuate.messaging.rocketmq.basic.consumer.EventuateRocketMQConsumerMessageHandler;
import help.lixin.eventuate.messaging.rocketmq.basic.consumer.RocketMQConsumerFactory;
import io.eventuate.messaging.partitionmanagement.CommonMessageConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MessageConsumerRocketMQImpl implements CommonMessageConsumer {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final String id = UUID.randomUUID().toString();

  private final String bootstrapServers;
  private final List<EventuateRocketMQConsumer> consumers = new ArrayList<>();
  private final EventuateRocketMQConsumerConfigurationProperties eventuateKafkaConsumerConfigurationProperties;
  private final RocketMQConsumerFactory kafkaConsumerFactory;

  public MessageConsumerRocketMQImpl(String bootstrapServers, //
                                     EventuateRocketMQConsumerConfigurationProperties eventuateKafkaConsumerConfigurationProperties,//
                                     RocketMQConsumerFactory kafkaConsumerFactory) {
    this.bootstrapServers = bootstrapServers;
    this.eventuateKafkaConsumerConfigurationProperties = eventuateKafkaConsumerConfigurationProperties;
    this.kafkaConsumerFactory = kafkaConsumerFactory;
  }

  public RocketMQSubscription subscribe(String subscriberId, Set<String> channels, RocketMQMessageHandler handler) {

    EventuateRocketMQConsumerMessageHandler wrapperHandler = (messageExt) -> {
      String body = new String(messageExt.getBody());
      RocketMQMessage msg = new RocketMQMessage(body);
      handler.accept(msg);
    };

    EventuateRocketMQConsumer kc = new EventuateRocketMQConsumer(
            //
            subscriberId,
            //
            wrapperHandler,
            //
            channels,
            //
            bootstrapServers,
            //
            eventuateKafkaConsumerConfigurationProperties,
            //
            kafkaConsumerFactory);
    consumers.add(kc);
    kc.start();
    return new RocketMQSubscription(() -> {
      kc.stop();
      consumers.remove(kc);
    });
  }

  @Override
  public void close() {
    consumers.forEach(EventuateRocketMQConsumer::stop);
  }

  public String getId() {
    return id;
  }
}
