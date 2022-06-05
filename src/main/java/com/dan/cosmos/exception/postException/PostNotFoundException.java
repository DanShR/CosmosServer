package com.dan.cosmos.exception.postException;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException() {
        super("Post not found");
    }
}
