package com.tinqinacademy.authentication.api.base;

import com.tinqinacademy.authentication.api.exceptions.Errors;
import io.vavr.control.Either;

public interface OperationProcessor<O extends OperationOutput, I extends OperationInput> {
    Either<Errors, O> process(I input);
}
