package com.dan.cosmos.exception.userException;

import com.dan.cosmos.exception.AbstractCustomException;

public class UserDisabledException extends AbstractCustomException {
    public UserDisabledException() {
        super("User disabled");
    }
}
