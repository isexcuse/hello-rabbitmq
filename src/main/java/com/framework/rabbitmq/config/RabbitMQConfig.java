package com.framework.rabbitmq.config;

import com.framework.rabbitmq.constants.MessageQueueConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: RabbitMQ配置中心
 * @author: XiongFeiYang
 * @createTime: 2019-07-07 14:37
 **/
@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }


    /**
     * 默认及时消息交换机
     *
     * @return DirectExchange
     */
    @Bean("defaultDirectExchange")
    public DirectExchange defaultDirectExchange() {
        return new DirectExchange(MessageQueueConstants.DEFAULT_DIRECT_EXCHANGE_NAME, true, false);
    }


    /**
     * 默认广播交换机对象
     *
     * @return FanoutExchange
     */
    @Bean("defaultFanoutExchange")
    public FanoutExchange defaultFanoutExchange() {
        return new FanoutExchange(MessageQueueConstants.DEFAULT_FANOUT_EXCHANGE_NAME, true, false);
    }


    /**
     * 默认topic路由方式交换机
     *
     * @return TopicExchange
     */
    @Bean("defaultTopicExchange")
    public TopicExchange defaultTopicExchange() {
        return new TopicExchange(MessageQueueConstants.DEFAULT_TOPIC_EXCHANGE_NAME, true, false);
    }


    /**
     * 默认headers交换机
     *
     * @return HeadersExchange
     */
    @Bean("defaultHeadersExchange")
    public HeadersExchange defaultHeadersExchange() {
        return new HeadersExchange(MessageQueueConstants.DEFAULT_HEADERS_EXCHANGE_NAME, true, false);
    }


    /**
     * 默认延迟消息死信队列
     *
     * @return Queue
     */
    @Bean
    public Queue defaultDeadLetterQueue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", MessageQueueConstants.DEFAULT_DIRECT_EXCHANGE_NAME);//设置交换机路由
        arguments.put("x-dead-letter-routing-key", MessageQueueConstants.DEFAULT_REPEAT_TRADE_QUEUE_NAME);//设置转发队列名称
        return new Queue(MessageQueueConstants.DEFAULT_DEAD_LETTER_QUEUE_NAME, true, false, false, arguments);
    }

    @Bean
    public Binding defaultDeadLetterBinding() {
        return BindingBuilder.bind(defaultDeadLetterQueue()).to(defaultDirectExchange()).with(MessageQueueConstants.DEFAULT_DEAD_LETTER_QUEUE_NAME);
    }


    /**
     * 默认延迟消息死信接受转发消息队列
     *
     * @return Queue
     */
    @Bean
    public Queue defaultRepeatTradeQueue() {
        return new Queue(MessageQueueConstants.DEFAULT_REPEAT_TRADE_QUEUE_NAME, true, false, false);
    }


    @Bean
    public Binding defaultRepeatTradeBinding() {
        return BindingBuilder.bind(defaultRepeatTradeQueue()).to(defaultDirectExchange()).with(MessageQueueConstants.DEFAULT_REPEAT_TRADE_QUEUE_NAME);
    }

    @Bean
    public Queue activityDefaultQueue() {
        return new Queue(MessageQueueConstants.ACTIVITY_DEFAULT_WEB_QUEUE_NAME, true, false, false);
    }

    @Bean
    public Binding activityDefaultBinding() {
        return BindingBuilder.bind(activityDefaultQueue()).to(defaultDirectExchange()).with(MessageQueueConstants.ACTIVITY_DEFAULT_WEB_QUEUE_NAME);
    }
}
