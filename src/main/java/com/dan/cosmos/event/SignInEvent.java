package com.dan.cosmos.event;

import org.springframework.context.ApplicationEvent;

public class SignInEvent extends ApplicationEvent {
    private String message;

    public SignInEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
