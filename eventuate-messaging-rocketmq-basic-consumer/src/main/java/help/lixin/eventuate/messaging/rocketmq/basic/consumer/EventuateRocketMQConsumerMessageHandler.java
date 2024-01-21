package help.lixin.eventuate.messaging.rocketmq.basic.consumer;

import org.apache.rocketmq.common.message.MessageExt;

import java.util.function.Consumer;

public interface EventuateRocketMQConsumerMessageHandler extends Consumer<MessageExt> {
}
