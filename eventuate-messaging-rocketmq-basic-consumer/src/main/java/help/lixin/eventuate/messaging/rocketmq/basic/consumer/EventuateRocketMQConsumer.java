package help.lixin.eventuate.messaging.rocketmq.basic.consumer;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;


public class EventuateRocketMQConsumer {

  private static Logger logger = LoggerFactory.getLogger(EventuateRocketMQConsumer.class);
  private final String subscriberId;
  private final EventuateRocketMQConsumerMessageHandler handler;
  private final Set<String> topics;
  private final RocketMQConsumerFactory rocketMQConsumerFactory;
  private final long pollTimeout;
  private AtomicBoolean stopFlag = new AtomicBoolean(false);
  private Properties consumerProperties;
  private volatile EventuateRocketMQConsumerState state = EventuateRocketMQConsumerState.CREATED;

  private volatile boolean closeConsumerOnStop = true;

  public EventuateRocketMQConsumer(String subscriberId, //
                                   EventuateRocketMQConsumerMessageHandler handler, //
                                   Set<String> topics, //
                                   String bootstrapServers, //
                                   EventuateRocketMQConsumerConfigurationProperties eventuateRocketMQConsumerConfigurationProperties, //
                                   RocketMQConsumerFactory rocketMQConsumerFactory) {
    this.subscriberId = subscriberId;
    this.handler = handler;
    this.topics = topics;
    this.rocketMQConsumerFactory = rocketMQConsumerFactory;

    // 创建默认的参数
    this.consumerProperties = ConsumerPropertiesFactory.makeDefaultConsumerProperties(bootstrapServers, subscriberId);
    // 添加扩展参数
    this.consumerProperties.putAll(eventuateRocketMQConsumerConfigurationProperties.getProperties());
    this.pollTimeout = eventuateRocketMQConsumerConfigurationProperties.getPollTimeout();
  }

  public boolean isCloseConsumerOnStop() {
    return closeConsumerOnStop;
  }

  public void setCloseConsumerOnStop(boolean closeConsumerOnStop) {
    this.closeConsumerOnStop = closeConsumerOnStop;
  }

  /**
   * 在订阅时,如果topic不存在,则创建topic
   *
   * @param consumer
   * @param topics
   * @return
   */
  public static List<MessageQueue> verifyTopicExistsBeforeSubscribing(RocketMQMessageConsumer consumer, Set<String> topics) {
    List<MessageQueue> queueList = new ArrayList<>(0);
    try {
      for (String topic : topics) {
        logger.debug("Verifying Topic {}", topic);
        try {
          List<MessageQueue> queues = consumer.partitionsFor(topic);
          queueList.addAll(queues);
          logger.debug("Got these partitions {} for Topic {}", queues, topic);
        } catch (RuntimeException e) {
          // 创建topic
          consumer.createTopic(topic);
          List<MessageQueue> queues = consumer.partitionsFor(topic);
          queueList.addAll(queues);
          logger.debug("Got these partitions {} for Topic {}", queues, topic);
        }
      }
      return queueList;
    } catch (Throwable e) {
      logger.error("Got exception: ", e);
      throw new RuntimeException(e);
    }
  }

  public void start() {
    try {
      // 通过工厂创建消费者(subscriberId: 为消费者组id)
      RocketMQMessageConsumer consumer = rocketMQConsumerFactory.makeConsumer(subscriberId, consumerProperties);
      consumer.register((messages) -> {
        for (MessageExt message : messages) {
          handler.accept(message);
        }
      });

      consumer.start();

      verifyTopicExistsBeforeSubscribing(consumer, topics);
      subscribe(consumer);

      state = EventuateRocketMQConsumerState.STARTED;
    } catch (Exception e) {
      logger.error("Error subscribing", e);
      state = EventuateRocketMQConsumerState.FAILED_TO_START;
      throw new RuntimeException(e);
    }
  }

  private void subscribe(RocketMQMessageConsumer consumer) {
    for (String topic : topics) {
      logger.debug("Subscribing to {} {}", subscriberId, topic);
      consumer.subscribe(topic);
      logger.debug("Subscribed to {} {}", subscriberId, topic);
    }
  }

  public void stop() {
    stopFlag.set(true);
  }

  public EventuateRocketMQConsumerState getState() {
    return state;
  }
}
