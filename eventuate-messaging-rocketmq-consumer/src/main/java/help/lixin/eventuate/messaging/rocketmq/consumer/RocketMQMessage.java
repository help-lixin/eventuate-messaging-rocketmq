package help.lixin.eventuate.messaging.rocketmq.consumer;

public class RocketMQMessage {
  private String payload;

  public RocketMQMessage(String payload) {
    this.payload = payload;
  }

  public String getPayload() {
    return payload;
  }
}
