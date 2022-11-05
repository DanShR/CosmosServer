package com.dan.cosmos.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Bean(name = "passwordRecoverQueue")
    public Queue passwordRecoveryQueue(@Value("${queue.notification.recoverpassword}") String passwordRecoverQueueName) {
        return new Queue(passwordRecoverQueueName);
    }

    @Bean(name = "signUpQueue")
    public Queue signUpQueue(@Value("${queue.notification.signup}") String signUpQueueName) {
        return new Queue(signUpQueueName);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
}
