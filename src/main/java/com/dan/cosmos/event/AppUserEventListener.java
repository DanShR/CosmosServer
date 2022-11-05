package com.dan.cosmos.event;

import com.dan.cosmos.service.NotificationService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AppUserEventListener {

    NotificationService notificationService;

    public AppUserEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @EventListener
    @Async
    public void onApplicationEvent(SignUpEvent signUpEvent) {
        notificationService.signUp(signUpEvent.getAppUser());
    }

    @EventListener
    @Async
    public void onApplicationEvent(RecoverPasswordEvent recoverPasswordEvent) {
        notificationService.passwordRecovery(recoverPasswordEvent.getAppUser());
    }
}