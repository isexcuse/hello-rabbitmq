package com.framework.rabbitmq.rabbitmq.consumer;

import com.framework.rabbitmq.constants.MessageQueueConstants;
import com.framework.rabbitmq.rabbitmq.domain.ActivityQueueMessage;
import com.framework.rabbitmq.rabbitmq.domain.QueueMessage;
import com.framework.rabbitmq.rabbitmq.provider.RabbitmqService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @description: RabbitMQ 死信接收处理消费者
 * @author: XiongFeiYang
 * @createTime: 2019-07-07 18:00
 **/
@Component
@RabbitListener(queues = MessageQueueConstants.DEFAULT_REPEAT_TRADE_QUEUE_NAME)
public class RabbitmqTradeConsumer {

    @Autowired
    private RabbitmqService rabbitmqService;

    @RabbitHandler
    public void process(QueueMessage<ActivityQueueMessage> message) {
        Logger logger = LoggerFactory.getLogger(RabbitmqTradeConsumer.class);
        logger.info("死信队列接收处理时间：" + LocalDateTime.now().toString());
        rabbitmqService.sendMessage(message);
    }

}
