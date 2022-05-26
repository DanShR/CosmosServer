package com.dan.cosmos.exception.userException;

import com.dan.cosmos.exception.AbstractCustomException;

public class EmailExistException extends AbstractCustomException {
    public EmailExistException() {
        super("Email is already in use");
    }
}
