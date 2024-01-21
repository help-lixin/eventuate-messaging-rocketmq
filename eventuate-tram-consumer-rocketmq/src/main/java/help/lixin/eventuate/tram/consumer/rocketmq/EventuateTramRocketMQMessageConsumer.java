package help.lixin.eventuate.tram.consumer.rocketmq;

import help.lixin.eventuate.messaging.rocketmq.consumer.MessageConsumerRocketMQImpl;
import help.lixin.eventuate.messaging.rocketmq.consumer.RocketMQMessage;
import help.lixin.eventuate.messaging.rocketmq.consumer.RocketMQMessageHandler;
import help.lixin.eventuate.messaging.rocketmq.consumer.RocketMQSubscription;
import io.eventuate.common.json.mapper.JSonMapper;
import io.eventuate.tram.consumer.common.MessageConsumerImplementation;
import io.eventuate.tram.messaging.common.MessageImpl;
import io.eventuate.tram.messaging.consumer.MessageHandler;
import io.eventuate.tram.messaging.consumer.MessageSubscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class EventuateTramRocketMQMessageConsumer implements MessageConsumerImplementation {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private MessageConsumerRocketMQImpl messageConsumerRocketmq;

    public EventuateTramRocketMQMessageConsumer(MessageConsumerRocketMQImpl messageConsumerRocketmq) {
        this.messageConsumerRocketmq = messageConsumerRocketmq;
    }

    @Override
    public MessageSubscription subscribe(String subscriberId, Set<String> channels, MessageHandler handler) {
        logger.info("Subscribing: subscriberId = {}, channels = {}", subscriberId, channels);

        RocketMQMessageHandler rocketMQMessageHandler = new RocketMQMessageHandler() {
            @Override
            public void accept(RocketMQMessage message) {
                handler.accept(JSonMapper.fromJson(message.getPayload(), MessageImpl.class));
            }
        };

        RocketMQSubscription subscription = messageConsumerRocketmq.subscribe(subscriberId, channels, rocketMQMessageHandler);
        logger.info("Subscribed: subscriberId = {}, channels = {}", subscriberId, channels);
        return subscription::close;
    }

    @Override
    public String getId() {
        return messageConsumerRocketmq.getId();
    }

    @Override
    public void close() {
        logger.info("Closing consumer");
        messageConsumerRocketmq.close();
        logger.info("Closed consumer");
    }
}
