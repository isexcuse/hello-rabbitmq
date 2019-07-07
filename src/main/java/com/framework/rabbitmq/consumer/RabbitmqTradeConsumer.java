package com.framework.rabbitmq.consumer;

import com.framework.rabbitmq.constants.MessageQueueConstants;
import com.framework.rabbitmq.service.RabbitMessageQueueService;
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
    private RabbitMessageQueueService rabbitMessageQueueService;

    @RabbitHandler
    public void process(String message) {
        System.out.println("死信接收处理时间：" + LocalDateTime.now().toString());
        rabbitMessageQueueService.sendMessage(MessageQueueConstants.QUEUE_HELLO_NAME, message);
    }

}
