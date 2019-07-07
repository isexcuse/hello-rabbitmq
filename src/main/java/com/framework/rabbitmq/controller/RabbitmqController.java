package com.framework.rabbitmq.controller;

import com.framework.rabbitmq.constants.MessageQueueConstants;
import com.framework.rabbitmq.service.RabbitMessageQueueService;
import com.framework.rabbitmq.vo.response.api.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: RabbitMQ请求入口
 * @author: XiongFeiYang
 * @createTime: 2019-07-07 16:16
 **/
@Api(description = "RabbitMQ 发送消息测试入口")
@RestController
@RequestMapping("/rabbit")
public class RabbitmqController {

    @Autowired
    private RabbitMessageQueueService rabbitMessageQueueService;

    @ApiOperation("发送异步消息")
    @GetMapping("/send")
    public ApiResponse sendMessage(@RequestParam("message") String message) {
        return rabbitMessageQueueService.sendMessage(MessageQueueConstants.QUEUE_HELLO_NAME, message);
    }

    @ApiOperation("发送延迟异步消息")
    @GetMapping("/sendDelayed")
    public ApiResponse sendDelayedMessage(@RequestParam("message") String message, @RequestParam("expiration") long expiration) {
        return rabbitMessageQueueService.sendDelayedMessage(MessageQueueConstants.QUEUE_HELLO_NAME, message, expiration);
    }
}
