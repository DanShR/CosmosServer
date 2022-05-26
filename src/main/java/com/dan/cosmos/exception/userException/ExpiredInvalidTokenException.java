package com.dan.cosmos.exception.userException;

import com.dan.cosmos.exception.AbstractCustomException;

public class ExpiredInvalidTokenException extends AbstractCustomException {
    public ExpiredInvalidTokenException() {
        super("Expired or invalid JWT token");
    }
}
