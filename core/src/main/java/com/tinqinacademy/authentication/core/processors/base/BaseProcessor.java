package com.tinqinacademy.authentication.core.processors.base;

import com.tinqinacademy.authentication.api.base.OperationInput;
import com.tinqinacademy.authentication.api.exceptions.Errors;
import com.tinqinacademy.authentication.api.exceptions.customExceptions.ViolationsException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public abstract class BaseProcessor {
    protected final ConversionService conversionService;
   // protected final ExceptionService exceptionService;
    private final Validator validator;

    protected <I extends OperationInput> void validate(I input) {
        Set<ConstraintViolation<I>> violationSet = validator.validate(input);

        if (!violationSet.isEmpty()) {
            List<Errors> errorList = violationSet
                    .stream()
                    .map(violation -> Errors.builder()
                            .message(violation.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build())
                    .collect(Collectors.toList());
            throw new ViolationsException("Violation exception occurred.", errorList);
        }
    }
}
