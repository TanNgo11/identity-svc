package com.shadcn.identity.util;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}