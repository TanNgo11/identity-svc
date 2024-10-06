package com.shadcn.identity.util;


import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {
    private final int code;

    // Constructor that accepts both message and code
    public BadRequestException(String message, int code) {
        super(message);
        this.code = code;
    }

}