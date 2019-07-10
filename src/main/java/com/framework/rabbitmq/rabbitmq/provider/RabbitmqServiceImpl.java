package com.framework.rabbitmq.rabbitmq.provider;

import com.framework.rabbitmq.constants.MessageQueueConstants;
import com.framework.rabbitmq.rabbitmq.domain.ActivityQueueMessage;
import com.framework.rabbitmq.rabbitmq.domain.QueueMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @description: Rabbit消息队列相关服务接口实现
 * @author: XiongFeiYang
 * @createTime: 2019-07-07 14:35
 **/
@Service
public class RabbitmqServiceImpl implements RabbitmqService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private Logger logger = LoggerFactory.getLogger(RabbitmqServiceImpl.class);

    @Override
    public void sendMessage(QueueMessage<ActivityQueueMessage> message) {
        logger.info("消息发送时间：" + LocalDateTime.now().toString());
        rabbitTemplate.convertAndSend(message.getQueueName(), message.getMessage());
    }

    @Override
    public void sendDelayedMessage(QueueMessage<ActivityQueueMessage> message) {
        long expiration = message.getExpiration();
        logger.info("延迟时间 expiration：" + expiration);
        // 直接发送
        if (expiration <= 0) {
            this.sendMessage(message);
            return;
        }
        // 延迟处理 默认毫秒级
        MessagePostProcessor processor = message1 -> {
            message1.getMessageProperties().setExpiration(String.valueOf(expiration));
            return message1;
        };
        logger.info("延迟队列消息发送时间：" + LocalDateTime.now().toString());
        rabbitTemplate.convertAndSend(MessageQueueConstants.DEFAULT_DEAD_LETTER_QUEUE_NAME, message, processor);
    }
}
