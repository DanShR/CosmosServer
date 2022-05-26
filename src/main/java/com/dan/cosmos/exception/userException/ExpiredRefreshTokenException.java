package com.dan.cosmos.exception.userException;

import com.dan.cosmos.exception.AbstractCustomException;

public class ExpiredRefreshTokenException extends AbstractCustomException {
    public ExpiredRefreshTokenException() {
        super("Expired refresh token");
    }
}
