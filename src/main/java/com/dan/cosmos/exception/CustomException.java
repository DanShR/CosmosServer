package com.dan.cosmos.exception;

import lombok.Getter;

@Getter
public class CustomException extends AbstractCustomException {

    private static final long serialVersionUID = 1L;

    private final String message;

    public CustomException(String message) {
        super(message);
        this.message = message;

    }
}
