package com.dan.cosmos.event;

import com.dan.cosmos.model.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {

    private ApplicationEventPublisher applicationEventPublisher;

    public EventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishSignUpEvent(final AppUser appUser) {
        SignUpEvent signUpEvent = new SignUpEvent(this, appUser);
        applicationEventPublisher.publishEvent(signUpEvent);
    }

    public void publishRecoverPasswordEvent(final AppUser appUser) {
        RecoverPasswordEvent recoverPasswordEvent = new RecoverPasswordEvent(this, appUser);
        applicationEventPublisher.publishEvent(recoverPasswordEvent);
    }
}
