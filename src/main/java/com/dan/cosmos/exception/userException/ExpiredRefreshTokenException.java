package com.dan.cosmos.exception.userException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ExpiredRefreshTokenException extends RuntimeException {
    public ExpiredRefreshTokenException() {
        super("Expired refresh token");
    }
}
