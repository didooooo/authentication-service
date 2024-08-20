package com.tinqinacademy.authentication.rest.controllers;

import com.tinqinacademy.authentication.api.exceptions.Errors;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {
    public ResponseEntity<?> handleTheEither(Either<Errors, ?> input) {
        if (input.isRight()) {
            return ResponseEntity.ok(input.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(input.getLeft());
    }

}
