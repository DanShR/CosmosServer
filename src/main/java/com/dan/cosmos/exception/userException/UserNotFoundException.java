package com.dan.cosmos.exception.userException;

import com.dan.cosmos.exception.AbstractCustomException;

public class UserNotFoundException extends AbstractCustomException {
    public UserNotFoundException() {
        super("User not found");
    }
}
