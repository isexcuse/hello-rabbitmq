package com.framework.rabbitmq.rabbitmq.provider;


import com.framework.rabbitmq.rabbitmq.domain.ActivityQueueMessage;
import com.framework.rabbitmq.rabbitmq.domain.QueueMessage;

/**
 * @description: Rabbit消息队列相关服务接口
 * @author: XiongFeiYang
 * @createTime: 2019-07-07 14:31
 **/
public interface RabbitmqService {

    /**
     * 发送消息到队列
     * @param message 消息传输对象
     * @return ApiResponse
     */
    void sendMessage(QueueMessage<ActivityQueueMessage> message);

    /**
     * 发送延迟消息到队列
     * @param message 消息传输对象
     * @return ApiResponse
     */
    void sendDelayedMessage(QueueMessage<ActivityQueueMessage> message);

}
