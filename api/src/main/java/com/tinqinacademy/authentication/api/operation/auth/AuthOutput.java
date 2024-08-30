package com.tinqinacademy.authentication.api.operation.auth;

import com.tinqinacademy.authentication.api.base.OperationOutput;
import lombok.*;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AuthOutput implements OperationOutput {
    private Boolean isJwtValid;
}