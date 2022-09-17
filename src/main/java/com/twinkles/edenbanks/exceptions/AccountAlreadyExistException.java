package com.twinkles.edenbanks.exceptions;

public class AccountAlreadyExistException extends RuntimeException {
    private int statusCode;
    public AccountAlreadyExistException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
