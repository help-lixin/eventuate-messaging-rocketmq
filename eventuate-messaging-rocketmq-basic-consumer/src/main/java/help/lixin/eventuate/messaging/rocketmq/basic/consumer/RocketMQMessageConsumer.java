package help.lixin.eventuate.messaging.rocketmq.basic.consumer;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;

public interface RocketMQMessageConsumer {

  void createTopic(final String topic);

  void start();

  void subscribe(String topic);

  List<MessageQueue> partitionsFor(String topic);

  void register(Consumer<List<MessageExt>> consumer);

  void close();
}
