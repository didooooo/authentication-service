package com.tinqinacademy.authentication.api.operation.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.authentication.api.base.OperationOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginOutput implements OperationOutput {
    @JsonIgnore
    private String jwt;
}
