package com.tinqinacademy.authentication.api.exceptions.customExceptions;

import com.tinqinacademy.authentication.api.exceptions.Errors;
import org.springframework.http.HttpStatus;

public class EmailNotFoundException extends Errors {
    public EmailNotFoundException(HttpStatus status, String message, Integer statusCode) {
        super(status, message, statusCode);
    }

    public EmailNotFoundException() {
    }
}
