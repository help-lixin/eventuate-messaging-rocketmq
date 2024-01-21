package help.lixin.eventuate.messaging.rocketmq.consumer;

public class RawRocketMQMessage {
  private final String messageKey;
  private final byte[] payload;

  public RawRocketMQMessage(String messageKey, byte[] payload) {
    this.messageKey = messageKey;
    this.payload = payload;
  }

  public String getMessageKey() {
    return messageKey;
  }

  public byte[] getPayload() {
    return payload;
  }
}
