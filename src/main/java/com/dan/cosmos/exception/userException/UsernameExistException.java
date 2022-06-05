package com.dan.cosmos.exception.userException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UsernameExistException extends RuntimeException {
    public UsernameExistException() {
        super("Username is already in use");
    }
}
