package com.framework.rabbitmq.service;

import com.framework.rabbitmq.vo.response.api.ApiResponse;

/**
 * @description: Rabbit消息队列相关服务接口
 * @author: XiongFeiYang
 * @createTime: 2019-07-07 14:31
 **/
public interface RabbitMessageQueueService {

    /**
     * 发送消息到队列
     * @param queueName 队列名称
     * @param message 消息内容
     * @return ApiResponse
     */
    ApiResponse sendMessage(String queueName, String message);

    /**
     * 发送延迟消息到队列
     * @param queueName 队列名称
     * @param message 消息内容
     * @param expiration 延迟时间 单位/分钟
     * @return ApiResponse
     */
    ApiResponse sendDelayedMessage(String queueName, String message, long expiration);

}
