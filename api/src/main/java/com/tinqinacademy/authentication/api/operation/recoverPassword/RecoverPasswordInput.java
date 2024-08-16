package com.tinqinacademy.authentication.api.operation.recoverPassword;

import com.tinqinacademy.authentication.api.base.OperationInput;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecoverPasswordInput implements OperationInput {
    @Email
    private String email;
}
