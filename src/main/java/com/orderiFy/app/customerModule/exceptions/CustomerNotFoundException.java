package com.orderiFy.app.customerModule.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;


public class CustomerNotFoundException extends RuntimeException {

    // Constructor that accepts a message
    public CustomerNotFoundException(String message) {
        super(message);
    }

    // Constructor that accepts a message and cause
    public CustomerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
