package com.framework.rabbitmq.rabbitmq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.framework.rabbitmq.constants.MessageQueueConstants;
import com.framework.rabbitmq.enums.ActivityState;
import com.framework.rabbitmq.rabbitmq.domain.ActivityQueueMessage;
import com.framework.rabbitmq.rabbitmq.domain.QueueMessage;
import com.framework.rabbitmq.rabbitmq.provider.RabbitmqService;
import com.framework.rabbitmq.utils.DateUtil;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;


/**
 * @description: RabbitMQ消费者
 * @author: XiongFeiYang
 * @createTime: 2019-07-07 16:32
 **/
@Component
@RabbitListener(queues = MessageQueueConstants.ACTIVITY_DEFAULT_WEB_QUEUE_NAME)
public class RabbitmqConsumer {

    @Autowired
    private RabbitmqService rabbitmqService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private Logger logger = LoggerFactory.getLogger(RabbitmqConsumer.class);

    @RabbitHandler
    public void process(ActivityQueueMessage message) {
        logger.info("消息接收时间：" + LocalDateTime.now().toString());
        logger.info("消费端接受消息：" + message.toString());
        ActivityState currentState = ActivityState.getCode(message.getCurrentState());
        if (Objects.isNull(currentState)) {
            throw new RuntimeException("Rabbit延迟处理出错【当前活动状态为空!】");
        }
        // 获取活动开始结束时间
        String cacheData = redisTemplate.opsForValue().get(message.getCurrentKey());
        logger.info("Rabbit延迟处理获取缓存数据：" + cacheData);
        if (Strings.isNullOrEmpty(cacheData)) return;
        // 获取活动开始结束时间
        JSONObject cacheJsonData = JSONObject.parseObject(cacheData);
        String activityStartTime = cacheJsonData.getString("activityStartTime");
        String activityEndTime = cacheJsonData.getString("activityEndTime");
        // 开始时间戳
        long startSecond = DateUtil.covertStrToTimestamp(activityStartTime);
        // 结束时间戳
        long endSecond = DateUtil.covertStrToTimestamp(activityEndTime);
        messageHandler(message, startSecond, endSecond);
    }


    /**
     * @return void
     * @description 消息处理
     * @author xiongfeiyang
     * @date 2019/7/8 18:13
     * @params [message, currentState, cacheData]
     */
    private void messageHandler(ActivityQueueMessage message, long activityStartTime, long activityEndTime) {
        rabbitmqService.sendDelayedMessage(QueueMessage.sendMessage(message, activityStartTime - activityEndTime));
    }

}
