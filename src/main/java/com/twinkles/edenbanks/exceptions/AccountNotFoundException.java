package com.twinkles.edenbanks.exceptions;

public class AccountNotFoundException extends RuntimeException {
    private int statusCode;
    public AccountNotFoundException(String message, int statusCode){
        super(message);
        this.statusCode = statusCode;
    }
}
