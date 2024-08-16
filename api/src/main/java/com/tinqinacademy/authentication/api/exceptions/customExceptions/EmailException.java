package com.tinqinacademy.authentication.api.exceptions.customExceptions;

import com.tinqinacademy.authentication.api.exceptions.Errors;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
public class EmailException extends Errors {
    public EmailException(HttpStatus status, String message, Integer statusCode) {
        super(status, message, statusCode);
    }
}
