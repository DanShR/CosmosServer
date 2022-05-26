package com.dan.cosmos.exception.userException;

import com.dan.cosmos.exception.AbstractCustomException;

public class RefreshTokenNotPresentException extends AbstractCustomException {
    public RefreshTokenNotPresentException() {
        super("Refresh token not present");
    }
}
