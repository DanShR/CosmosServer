package com.dan.cosmos.exception.userException;

import com.dan.cosmos.exception.AbstractCustomException;

public class InvalidUsernamePasswordException extends AbstractCustomException {
    public InvalidUsernamePasswordException() {
        super("Invalid username/password supplied");
    }
}
