package com.dan.cosmos.service;

import com.dan.cosmos.model.AppUser;
import com.dan.cosmos.rabbitmq.QueuePasswordRecoveryDTO;
import com.dan.cosmos.rabbitmq.QueueSignUpDTO;
import com.dan.cosmos.rabbitmq.SenderRabbitMQ;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private SenderRabbitMQ senderRabbitMQ;
    private final ModelMapper modelMapper;

    public NotificationService(SenderRabbitMQ senderRabbitMQ, ModelMapper modelMapper) {
        this.senderRabbitMQ = senderRabbitMQ;
        this.modelMapper = modelMapper;
    }

    public void signUp(AppUser appUser) {
        final QueueSignUpDTO queueSignUpDTO = modelMapper.map(appUser, QueueSignUpDTO.class);
        senderRabbitMQ.signUp(queueSignUpDTO);
    }

    public void passwordRecovery(AppUser appUser) {
        final QueuePasswordRecoveryDTO queuePasswordRecoveryDTO = modelMapper.map(appUser, QueuePasswordRecoveryDTO.class);
        senderRabbitMQ.passwordRecovery(queuePasswordRecoveryDTO);
    }
}
