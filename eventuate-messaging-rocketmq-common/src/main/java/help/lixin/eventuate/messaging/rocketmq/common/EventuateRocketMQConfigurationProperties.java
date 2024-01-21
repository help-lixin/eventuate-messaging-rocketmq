package help.lixin.eventuate.messaging.rocketmq.common;

public class EventuateRocketMQConfigurationProperties {

  private String nameServerAddress;

  public EventuateRocketMQConfigurationProperties(String nameServerAddress) {
    this.nameServerAddress = nameServerAddress;
  }

  public String getNameServerAddress() {
    return nameServerAddress;
  }

  public void setNameServerAddress(String nameServerAddress) {
    this.nameServerAddress = nameServerAddress;
  }
}
