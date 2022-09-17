package com.twinkles.edenbanks.exceptions;

public class InsufficientBalanceException extends RuntimeException{
    private int statusCode;
    public InsufficientBalanceException(String message, int statusCode){
        super(message);
        this.statusCode = statusCode;
    }

}
