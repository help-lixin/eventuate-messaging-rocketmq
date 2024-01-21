package help.lixin.eventuate.messaging.rocketmq.spring.producer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventuateRocketMQProducerSpringConfigurationPropertiesConfiguration.class)
public class EventuateKafkaProducerConfigurationSpringTest {

  @Autowired
  private EventuateRocketMQProducerSpringConfigurationProperties eventuateRocketMQProducerSpringConfigurationProperties;

  @Test
  public void testPropertyParsing() {
    Assert.assertEquals(3, eventuateRocketMQProducerSpringConfigurationProperties.getProperties().size());
    Assert.assertEquals("1000000", eventuateRocketMQProducerSpringConfigurationProperties.getProperties().get("buffer.memory"));
    Assert.assertEquals("org.apache.kafka.common.serialization.ByteArraySerializer",
            eventuateRocketMQProducerSpringConfigurationProperties.getProperties().get("value.serializer"));
  }
}
