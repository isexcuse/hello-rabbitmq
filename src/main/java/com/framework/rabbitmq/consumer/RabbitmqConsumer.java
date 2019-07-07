package com.framework.rabbitmq.consumer;

import com.framework.rabbitmq.constants.MessageQueueConstants;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @description: RabbitMQ消费者
 * @author: XiongFeiYang
 * @createTime: 2019-07-07 16:32
 **/
@Component
@RabbitListener(queues = MessageQueueConstants.QUEUE_HELLO_NAME)
public class RabbitmqConsumer {

    @RabbitHandler
    public void process(String message) {
        System.out.println("接收时间：" + LocalDateTime.now().toString());
        System.out.println("消费端接受消息：" + message);
    }

}
