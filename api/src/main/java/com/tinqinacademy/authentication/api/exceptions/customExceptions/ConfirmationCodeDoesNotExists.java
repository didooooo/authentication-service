package com.tinqinacademy.authentication.api.exceptions.customExceptions;

import com.tinqinacademy.authentication.api.exceptions.Errors;
import org.springframework.http.HttpStatus;

public class ConfirmationCodeDoesNotExists extends Errors {
    public ConfirmationCodeDoesNotExists(HttpStatus status, String message, Integer statusCode) {
        super(status, message, statusCode);
    }

    public ConfirmationCodeDoesNotExists() {
    }
}
