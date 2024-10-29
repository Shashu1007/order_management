package com.oderiFy.app.exception;

public class InvalidCredentialsException extends RuntimeException {
    // Constructor that accepts a message and a cause
    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor that accepts only a message
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
