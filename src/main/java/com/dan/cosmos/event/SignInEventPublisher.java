package com.dan.cosmos.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SignInEventPublisher {

    private ApplicationEventPublisher applicationEventPublisher;

    public SignInEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishCustomEvent(final String message) {
        System.out.println("Publishing custom event. ");
        SignInEvent signInEvent = new SignInEvent(this, message);
        applicationEventPublisher.publishEvent(signInEvent);
    }
}
