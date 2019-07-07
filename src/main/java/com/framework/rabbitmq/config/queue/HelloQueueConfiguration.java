package com.framework.rabbitmq.config.queue;

import com.framework.rabbitmq.constants.MessageQueueConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * hello队列配置
 * @author victor
 *
 */
@Configuration
public class HelloQueueConfiguration {

	@Autowired
	@Qualifier("defaultDirectExchange")
	private DirectExchange exchange;


	@Bean
	public Queue helloQueue() {
		return new Queue(MessageQueueConstants.QUEUE_HELLO_NAME,true,false,false);
	}

	@Bean
	public Binding helloBinding() {
		return BindingBuilder.bind(helloQueue()).to(exchange).with(MessageQueueConstants.QUEUE_HELLO_NAME);
	}

}
