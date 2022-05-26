package com.dan.cosmos.exception.userException;

import com.dan.cosmos.exception.AbstractCustomException;

public class UserAuthenticationException extends AbstractCustomException {
    public UserAuthenticationException(String message) {
        super(message);
    }
}
