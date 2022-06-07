package com.dan.cosmos.event;

import com.dan.cosmos.service.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AppUserEventListener {

    EmailService emailService;

    public AppUserEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    @Async
    public void onApplicationEvent(SignUpEvent signUpEvent) {
        emailService.sendConfirmEmail(signUpEvent.getAppUser());
    }

    @EventListener
    @Async
    public void onApplicationEvent(RecoverPasswordEvent recoverPasswordEvent) {
        emailService.sendPasswordRecoveryEmail(recoverPasswordEvent.getAppUser());
    }
}