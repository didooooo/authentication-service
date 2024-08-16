package com.tinqinacademy.authentication.api.operation.promoteUserToAdmin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.authentication.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PromoteUserToAdminInput implements OperationInput {
    @NotBlank
    private UUID userId;
    @JsonIgnore
    private java.util.UUID userContextId;
}
