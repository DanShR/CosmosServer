package com.dan.cosmos.exception.userException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class RefreshTokenNotPresentException extends RuntimeException {
    public RefreshTokenNotPresentException() {
        super("Refresh token not present");
    }
}
