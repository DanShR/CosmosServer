package com.dan.cosmos.exception.userException;

import com.dan.cosmos.exception.AbstractCustomException;

public class EmailAlreadyConfirmedException extends AbstractCustomException {
    public EmailAlreadyConfirmedException() {
        super("Email already confirmed");
    }
}
