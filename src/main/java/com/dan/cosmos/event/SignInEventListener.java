package com.dan.cosmos.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SignInEventListener {
    @EventListener
    public void onApplicationEvent(SignInEvent event) {
        System.out.println("Received spring custom event - " + event.getMessage());
    }
}