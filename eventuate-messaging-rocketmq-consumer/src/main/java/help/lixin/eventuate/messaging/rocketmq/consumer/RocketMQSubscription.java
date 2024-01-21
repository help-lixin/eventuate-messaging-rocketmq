package help.lixin.eventuate.messaging.rocketmq.consumer;

public class RocketMQSubscription {
  private Runnable closingCallback;

  public RocketMQSubscription(Runnable closingCallback) {
    this.closingCallback = closingCallback;
  }

  public void close() {
    closingCallback.run();
  }
}
