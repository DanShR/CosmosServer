package com.dan.cosmos.exception.userException;

import com.dan.cosmos.exception.AbstractCustomException;

public class UserLockedException extends AbstractCustomException {
    public UserLockedException() {
        super("User locked");
    }
}
