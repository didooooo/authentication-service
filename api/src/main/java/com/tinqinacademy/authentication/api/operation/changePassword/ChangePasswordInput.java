package com.tinqinacademy.authentication.api.operation.changePassword;

import com.tinqinacademy.authentication.api.base.OperationInput;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordInput implements OperationInput {
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;
    @Email
    private String email;
}
