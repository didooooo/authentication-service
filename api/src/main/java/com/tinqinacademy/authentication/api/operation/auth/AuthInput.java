package com.tinqinacademy.authentication.api.operation.auth;

import com.tinqinacademy.authentication.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AuthInput implements OperationInput {
    @NotBlank
    private String jwt;
}