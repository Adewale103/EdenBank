package com.twinkles.edenbanks.exceptions;

import lombok.Getter;

@Getter
public class UserAlreadyExistException extends RuntimeException {
    private int statusCode;
    public UserAlreadyExistException(String message, int statusCode){
        super(message);
        this.statusCode = statusCode;
    }

}
