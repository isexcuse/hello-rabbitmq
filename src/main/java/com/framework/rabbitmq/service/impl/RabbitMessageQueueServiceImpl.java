package com.framework.rabbitmq.service.impl;

import com.framework.rabbitmq.constants.MessageQueueConstants;
import com.framework.rabbitmq.service.RabbitMessageQueueService;
import com.framework.rabbitmq.vo.response.api.ApiResponse;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @description: Rabbit消息队列相关服务接口实现
 * @author: XiongFeiYang
 * @createTime: 2019-07-07 14:35
 **/
@Service
public class RabbitMessageQueueServiceImpl implements RabbitMessageQueueService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public ApiResponse sendMessage(String queueName, String message) {
        rabbitTemplate.convertAndSend(queueName, message);
        return ApiResponse.buildResponse(HttpStatus.OK.value(), "success");
    }

    @Override
    public ApiResponse sendDelayedMessage(String queueName, String message, long expiration) {
        // 直接发送
        if (expiration <= 0) return this.sendMessage(queueName, message);
        // 延迟处理 默认毫秒级
        MessagePostProcessor processor = message1 -> {
            message1.getMessageProperties().setExpiration(String.valueOf(expiration * 1000 * 60));
            return message1;
        };
        System.out.println("发送时间：" + LocalDateTime.now().toString());
        rabbitTemplate.convertAndSend(MessageQueueConstants.DEFAULT_DEAD_LETTER_QUEUE_NAME, (Object) message, processor);
        return ApiResponse.buildResponse(HttpStatus.OK.value(), "success");
    }
}
