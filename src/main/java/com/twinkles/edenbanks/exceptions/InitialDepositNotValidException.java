package com.twinkles.edenbanks.exceptions;

public class InitialDepositNotValidException extends RuntimeException{
    private int statusCode;
    public InitialDepositNotValidException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
