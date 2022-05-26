package com.dan.cosmos.exception.userException;

import com.dan.cosmos.exception.AbstractCustomException;

public class RefreshTokenNotFoundException extends AbstractCustomException {
    public RefreshTokenNotFoundException() {
        super("Refresh token not found");
    }
}
