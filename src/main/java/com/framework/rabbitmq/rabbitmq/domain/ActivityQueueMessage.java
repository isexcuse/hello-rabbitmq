package com.framework.rabbitmq.rabbitmq.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author XiongFeiYang
 * @description 活动队列消息传输对象
 * @createTime 2019-07-08 15:29
 **/
@Data
public class ActivityQueueMessage implements Serializable {

    private static final long serialVersionUID = -6659970389073867468L;

    /**
     * 当前活动状态
     */
    private Integer currentState;

    /**
     * 活动编号
     */
    private Long activityId;

    /**
     * 缓存key
     */
    private String currentKey;
}
