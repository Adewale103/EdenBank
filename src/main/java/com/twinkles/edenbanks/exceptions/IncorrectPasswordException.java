package com.twinkles.edenbanks.exceptions;

public class IncorrectPasswordException extends RuntimeException {
    private int statusCode;
    public IncorrectPasswordException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
