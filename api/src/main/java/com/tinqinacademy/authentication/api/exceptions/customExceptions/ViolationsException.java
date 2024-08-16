package com.tinqinacademy.authentication.api.exceptions.customExceptions;

import com.tinqinacademy.authentication.api.exceptions.Errors;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ViolationsException extends RuntimeException {
    private final List<Errors> errors;
    private final HttpStatus httpStatus;

    public ViolationsException(String msg, List<Errors> errorList) {
        super(msg);
        httpStatus = HttpStatus.BAD_REQUEST;
        errors = errorList;
    }
}
