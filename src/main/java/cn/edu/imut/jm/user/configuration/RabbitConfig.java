package cn.edu.imut.jm.user.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class RabbitConfig {

	public static final String MAIL_QUEUE = "message";

	@Bean
	public Queue mailQueue() {
		return new Queue(MAIL_QUEUE, true);
	}

	@Bean
	public ConnectionFactory factory() {
		CachingConnectionFactory factory = new CachingConnectionFactory();
		factory.setHost("127.0.0.1");
		factory.setPort(5672);
		factory.setUsername("admin");
		factory.setPassword("admin");
		return factory;
	}

	// 获取rabbitmqAdmin
	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory factory) {
		RabbitAdmin admin = new RabbitAdmin(factory);
		admin.setAutoStartup(true);
		return admin;
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
		RabbitTemplate template = new RabbitTemplate(factory);
		return template;
	}

	@Bean
	public SimpleMessageListenerContainer container(ConnectionFactory factory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(factory);
		container.setConsumerTagStrategy(new ConsumerTagStrategy() {

			public String createConsumerTag(String queue) {
				return null;
			}
		});
		return container;
	}

}
