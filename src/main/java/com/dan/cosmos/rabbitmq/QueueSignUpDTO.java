package com.dan.cosmos.rabbitmq;

import lombok.Data;

@Data
public class QueueSignUpDTO {
    private String username;
    private String email;
    private String passwordRecoveryToken;
}
