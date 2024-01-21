package help.lixin.eventuate.messaging.rocketmq.basic.consumer;

public class RocketMQMessageProcessorFailedException extends RuntimeException {
  public RocketMQMessageProcessorFailedException(Throwable t) {
    super(t);
  }
}
