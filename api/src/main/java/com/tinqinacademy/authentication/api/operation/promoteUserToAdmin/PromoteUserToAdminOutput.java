package com.tinqinacademy.authentication.api.operation.promoteUserToAdmin;

import com.tinqinacademy.authentication.api.base.OperationOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PromoteUserToAdminOutput implements OperationOutput {
    private String message;
}
