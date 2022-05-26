package com.dan.cosmos.exception.userException;

import com.dan.cosmos.exception.AbstractCustomException;

public class UsernameExistException extends AbstractCustomException {
    public UsernameExistException() {
        super("Username is already in use");
    }
}
