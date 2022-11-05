package com.dan.cosmos.rabbitmq;

import com.dan.cosmos.model.AppUser;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SenderRabbitMQ {

    @Resource(name = "signUpQueue")
    private final Queue signUpQueue;

    @Resource(name = "passwordRecoverQueue")
    private final Queue passwordRecoverQueue;
    private RabbitTemplate template;

    public SenderRabbitMQ(Queue signUpQueue,
                          Queue passwordRecoverQueue, RabbitTemplate template) {
        this.signUpQueue = signUpQueue;
        this.passwordRecoverQueue = passwordRecoverQueue;
        this.template = template;
    }

    public void signUp(QueueSignUpDTO queueSignUpDTO) {
        this.template.convertAndSend(signUpQueue.getName(), queueSignUpDTO);
    }

    public void passwordRecovery(QueuePasswordRecoveryDTO queuePasswordRecoveryDTO) {
        this.template.convertAndSend(passwordRecoverQueue.getName(), queuePasswordRecoveryDTO);
    }
}
