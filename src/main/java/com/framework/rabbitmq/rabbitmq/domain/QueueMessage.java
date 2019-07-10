package com.framework.rabbitmq.rabbitmq.domain;


import com.framework.rabbitmq.constants.MessageQueueConstants;

import java.io.Serializable;

/**
 * @author XiongFeiYang
 * @description RabbitMQ消息队列传输
 * @createTime 2019-07-08 14:34
 **/
public class QueueMessage<T> implements Serializable {

    private static final long serialVersionUID = -5863328509137879203L;

    /**
     * 队列名称
     */
    private String queueName;

    /**
     * 消息主体
     */
    private T message;

    /**
     * 延迟时间 单位/分钟
     */
    private long expiration;

    private QueueMessage() {

    }

    /**
     * 发送通用消息
     */
    public static <T> QueueMessage<T> sendMessage(String queueName, T message) {
        QueueMessage<T> tQueueMessage = new QueueMessage<>();
        tQueueMessage.setQueueName(queueName);
        tQueueMessage.setMessage(message);
        return tQueueMessage;
    }

    /**
     * 默认使用 ACTIVITY_DEFAULT_WEB_QUEUE_NAME 消息队列
     */
    public static <T> QueueMessage<T> sendMessage(T message, long expiration) {
        QueueMessage<T> tQueueMessage = new QueueMessage<>();
        tQueueMessage.setQueueName(MessageQueueConstants.ACTIVITY_DEFAULT_WEB_QUEUE_NAME);
        tQueueMessage.setMessage(message);
        tQueueMessage.setExpiration(expiration);
        return tQueueMessage;
    }

    public static <T> QueueMessage<T> sendMessage(String queueName, T message, long expiration) {
        QueueMessage<T> tQueueMessage = new QueueMessage<>();
        tQueueMessage.setQueueName(queueName);
        tQueueMessage.setMessage(message);
        tQueueMessage.setExpiration(expiration);
        return tQueueMessage;
    }


    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    @Override
    public String toString() {
        return "QueueMessage{" +
                "queueName='" + queueName + '\'' +
                ", message=" + message +
                ", expiration=" + expiration +
                '}';
    }
}
