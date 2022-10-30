package com.dan.cosmos.exception.userException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidUsernamePasswordException extends RuntimeException {
    public InvalidUsernamePasswordException() {
        super("Invalid username/password supplied");
    }
}
