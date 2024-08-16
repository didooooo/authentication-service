package com.tinqinacademy.authentication.api.operation.login;

import com.tinqinacademy.authentication.api.base.OperationInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginInput implements OperationInput {
    private String username;
    private String password;
}
