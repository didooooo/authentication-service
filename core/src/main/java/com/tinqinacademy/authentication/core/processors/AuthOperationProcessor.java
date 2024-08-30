package com.tinqinacademy.authentication.core.processors;

import com.tinqinacademy.authentication.api.exceptions.ErrorMapper;
import com.tinqinacademy.authentication.api.exceptions.Errors;
import com.tinqinacademy.authentication.api.operation.auth.AuthInput;
import com.tinqinacademy.authentication.api.operation.auth.AuthOperation;
import com.tinqinacademy.authentication.api.operation.auth.AuthOutput;
import com.tinqinacademy.authentication.core.processors.base.BaseProcessor;
import com.tinqinacademy.authentication.core.security.JwtService;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthOperationProcessor extends BaseProcessor implements AuthOperation {
    private final JwtService jwtService;
    private final ErrorMapper errorMapper;

    public AuthOperationProcessor(ConversionService conversionService, Validator validator, JwtService jwtService, ErrorMapper errorMapper) {
        super(conversionService, validator);
        this.jwtService = jwtService;
        this.errorMapper = errorMapper;
    }
    @Override
    public Either<Errors, AuthOutput> process(AuthInput input) {
        return Try.of(() -> {
                    validate(input);
                    Boolean isJWTValid = jwtService.validateJwt(input.getJwt());
                    AuthOutput output = AuthOutput.builder()
                            .isJwtValid(isJWTValid)
                            .build();
                    return output;
                })
                .toEither()
                .mapLeft(errorMapper::mapError);
    }


}
