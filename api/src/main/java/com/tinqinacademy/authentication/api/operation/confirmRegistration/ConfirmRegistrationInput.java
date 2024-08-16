package com.tinqinacademy.authentication.api.operation.confirmRegistration;

import com.tinqinacademy.authentication.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmRegistrationInput implements OperationInput {
    @NotBlank
    private String confirmationCode;
}
