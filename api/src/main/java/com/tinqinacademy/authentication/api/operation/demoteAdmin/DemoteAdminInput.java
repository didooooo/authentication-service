package com.tinqinacademy.authentication.api.operation.demoteAdmin;

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
public class DemoteAdminInput implements OperationInput {
    @NotBlank
    private UUID userId;
}
