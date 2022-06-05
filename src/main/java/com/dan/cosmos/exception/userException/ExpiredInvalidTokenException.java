package com.dan.cosmos.exception.userException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ExpiredInvalidTokenException extends RuntimeException {
    public ExpiredInvalidTokenException() {
        super("Expired or invalid JWT token");
    }
}
