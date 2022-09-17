package com.twinkles.edenbanks.exceptions;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    private int statusCode;
    public UserNotFoundException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
