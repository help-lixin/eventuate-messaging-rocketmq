package help.lixin.eventuate.messaging.rocketmq.spring.basic.consumer;

import help.lixin.eventuate.messaging.rocketmq.basic.consumer.*;
import help.lixin.eventuate.messaging.rocketmq.producer.EventuateRocketMQProducer;
import help.lixin.eventuate.messaging.rocketmq.producer.EventuateRocketMQProducerConfigurationProperties;
import help.lixin.eventuate.messaging.rocketmq.spring.producer.EventuateRocketMQProducerSpringConfigurationPropertiesConfiguration;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventuateKafkaConsumerTest.Config.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EventuateKafkaConsumerTest {

  @Configuration
  @Import({EventuateRocketMQConsumerSpringConfigurationPropertiesConfiguration.class, EventuateRocketMQProducerSpringConfigurationPropertiesConfiguration.class})
  public static class Config {
  }

  private Logger logger = LoggerFactory.getLogger(getClass());

  @Value("${eventuate.local.rocket.mq.nameserver.address}")
  private String bootstrapServers;

  @Autowired
  private EventuateRocketMQConsumerConfigurationProperties eventuateKafkaConsumerConfigurationProperties;

  @Autowired
  private EventuateRocketMQProducerConfigurationProperties eventuateKafkaProducerConfigurationProperties;

  private EventuateRocketMQProducer eventuateKafkaProducer;

  private EventuateRocketMQConsumerMessageHandler mockedHandler;

  private String subscriberId = "test-hello-world";

  private String uniqueId() {
    return UUID.randomUUID().toString();
  }

  private String topic = "hello-world";

  private LinkedBlockingQueue<byte[]> queue = new LinkedBlockingQueue<>();

  private IMQPushConsumerCustomizer consumerCustomizers = new DefaultLitePullConsumerCustomizer();

  private IMQProducerCustomizer producerCustomizer = new DefaultMQProducerCustomizer();

  private RocketMQConsumerFactory kafkaConsumerFactory = new DefaultRocketMQConsumerFactory(Arrays.asList(producerCustomizer), Arrays.asList(consumerCustomizers));

  @Before
  public void init() throws Exception {
    eventuateKafkaProducer = new EventuateRocketMQProducer(bootstrapServers, eventuateKafkaProducerConfigurationProperties);
  }

  @Test
  public void testSendAndRevice() {
    // 发送消息
    for (int i = 0; i < 5; i++) {
      sendMessage("test-value-" + i);
    }

    //创建消费者,用来接受消息.
    assertMessageReceivedByNewConsumer();
  }

  private void assertMessageReceivedByNewConsumer() {
    assertMessageReceivedByNewConsumer("test-value");
  }

  private void assertMessageReceivedByNewConsumer(String value) {
    EventuateRocketMQConsumerMessageHandler handler = new EventuateRocketMQConsumerMessageHandler() {
      @Override
      public void accept(MessageExt record) {
        // 测试有一条数据不能进行commit
        String msg = new String(record.getBody());
        if (msg.equals("test-value-2")) {
          throw new RuntimeException("出错了");
        }
        queue.add(record.getBody());
      }
    };

    createConsumer(handler);

    while (true) {
      byte[] m;
      try {
        m = queue.poll(10000, TimeUnit.SECONDS);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }

      // assertNotNull("Message not received by timeout", m);
      String message = new String(m);
      System.out.println("***************************************************");
      System.out.println(message);
      System.out.println("***************************************************");
      // Assert.assertEquals(value, message);
    }
  }

  private void sendMessage() {
    sendMessage("test-value");
  }

  private void sendMessage(String value) {
    try {
      eventuateKafkaProducer.send(topic, "test-key", value).get();
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  private EventuateRocketMQConsumer createConsumer(EventuateRocketMQConsumerMessageHandler handler) {
    EventuateRocketMQConsumer eventuateKafkaConsumer = new EventuateRocketMQConsumer(subscriberId, handler, Set.of(topic), bootstrapServers, eventuateKafkaConsumerConfigurationProperties, kafkaConsumerFactory);
    eventuateKafkaConsumer.start();
    return eventuateKafkaConsumer;
  }
}
