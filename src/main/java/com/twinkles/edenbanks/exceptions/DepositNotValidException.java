package com.twinkles.edenbanks.exceptions;

public class DepositNotValidException extends RuntimeException{
    private int statusCode;
    public DepositNotValidException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
