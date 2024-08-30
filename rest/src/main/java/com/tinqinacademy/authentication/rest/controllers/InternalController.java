package com.tinqinacademy.authentication.rest.controllers;

import com.tinqinacademy.authentication.api.exceptions.Errors;
import com.tinqinacademy.authentication.api.mappings.URLMapping;
import com.tinqinacademy.authentication.api.operation.auth.AuthInput;
import com.tinqinacademy.authentication.api.operation.auth.AuthOperation;
import com.tinqinacademy.authentication.api.operation.auth.AuthOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InternalController extends BaseController {

    private final AuthOperation authOperation;

    @Operation(summary = "Validate JWT.",
            description = "JWT is validated, and returns true/false whether it is valid or not.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Jwt has been validated successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Not found.")
    })
    @PostMapping(URLMapping.VALIDATE_JWT)
    public ResponseEntity<?> isJwtValid(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String jwt = authorizationHeader.substring(7);
        AuthInput input = AuthInput.builder()
                .jwt(jwt)
                .build();
        Either<Errors, AuthOutput> either = authOperation.process(input);
        return handleTheEither(either);
    }
}
