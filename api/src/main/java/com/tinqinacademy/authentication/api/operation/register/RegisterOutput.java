package com.tinqinacademy.authentication.api.operation.register;

import com.tinqinacademy.authentication.api.base.OperationOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterOutput implements OperationOutput {
    private UUID id;
}
