package help.lixin.eventuate.messaging.rocketmq.spring.basic.consumer;

import help.lixin.eventuate.messaging.rocketmq.basic.consumer.EventuateRocketMQConsumerConfigurationProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventuateRocketMQConsumerSpringConfigurationPropertiesConfiguration.class)
public class EventuateKafkaConsumerConfigurationSpringTest {

  @Autowired
  private EventuateRocketMQConsumerConfigurationProperties eventuateKafkaConsumerConfigurationProperties;

  @Test
  public void testPropertyParsing() {
    assertEquals(2, eventuateKafkaConsumerConfigurationProperties.getProperties().size());
    assertEquals("10000", eventuateKafkaConsumerConfigurationProperties.getProperties().get("session.timeout.ms"));
    assertEquals(200, eventuateKafkaConsumerConfigurationProperties.getPollTimeout());
  }
}
