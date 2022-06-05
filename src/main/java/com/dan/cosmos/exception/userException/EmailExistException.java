package com.dan.cosmos.exception.userException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class EmailExistException extends RuntimeException {
    public EmailExistException() {
        super("Email is already in use");
    }
}
