package com.dan.cosmos.exception.postException;

import com.dan.cosmos.exception.AbstractCustomException;

public class PostNotFoundException extends AbstractCustomException {
    public PostNotFoundException() {
        super("Post not found");
    }
}
