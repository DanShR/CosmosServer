package com.dan.cosmos.event;

import com.dan.cosmos.model.AppUser;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class SignUpEvent extends ApplicationEvent {

    @Getter
    private AppUser appUser;

    public SignUpEvent(Object source, AppUser appUser) {
        super(source);
        this.appUser = appUser;
    }

}
