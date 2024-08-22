package com.tinqinacademy.authentication.api.exceptions.customExceptions;

import com.tinqinacademy.authentication.api.exceptions.Errors;
import org.springframework.http.HttpStatus;

public class UserIsNotAdminException extends Errors {
    public UserIsNotAdminException(HttpStatus status, String message, Integer statusCode) {
        super(status, message, statusCode);
    }

    public UserIsNotAdminException() {
    }
}
