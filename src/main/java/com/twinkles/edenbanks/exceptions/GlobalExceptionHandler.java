package com.twinkles.edenbanks.exceptions;

import com.twinkles.edenbanks.dtos.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccountAlreadyExistException.class)
    public ResponseEntity<?> handleAccountAlreadyExistException(AccountAlreadyExistException accountAlreadyExistException){
        return new ResponseEntity<>(ApiResponse.builder().
                message(accountAlreadyExistException.getMessage()).
                statusCode(400).
                successful(false).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<?> handleAccountNotFoundException(AccountNotFoundException accountNotFoundException){
        return new ResponseEntity<>(ApiResponse.builder().
                message(accountNotFoundException.getMessage()).
                statusCode(404).
                successful(false).build(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DepositNotValidException.class)
    public ResponseEntity<?> handleDepositNotValidException(DepositNotValidException depositNotValidException){
        return new ResponseEntity<>(ApiResponse.builder().
                message(depositNotValidException.getMessage()).
                statusCode(400).
                successful(false).build(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<?> handleIncorrectPasswordException(IncorrectPasswordException incorrectPasswordException){
        return new ResponseEntity<>(ApiResponse.builder().
                message(incorrectPasswordException.getMessage()).
                statusCode(400).
                successful(false).build(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<?> handleInsufficientBalanceException(InsufficientBalanceException insufficientBalanceException){
        return new ResponseEntity<>(ApiResponse.builder().
                message(insufficientBalanceException.getMessage()).
                statusCode(400).
                successful(false).build(), HttpStatus.BAD_REQUEST);
    }
}

