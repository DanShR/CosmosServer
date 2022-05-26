package com.dan.cosmos.exception;

import lombok.Getter;

@Getter
public class AbstractCustomException extends RuntimeException {
    private final String message;

    public AbstractCustomException(String message) {
        this.message = message;
    }
}
