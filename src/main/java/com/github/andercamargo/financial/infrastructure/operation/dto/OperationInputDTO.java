package com.github.andercamargo.financial.infrastructure.operation.dto;

import com.github.andercamargo.financial.infrastructure.common.validation.formats.Name;
import com.github.andercamargo.financial.usecase.operation.dto.IOperationInputDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public record OperationInputDTO(
        @Name(message = "{wallet.name}")
        String walletName,
        @Name(message = "{customer.name}")
        String customerName,
        @Min(0)
        @Max(value = 10000000, message = "{operation.amount}")
        BigDecimal amount) implements IOperationInputDTO {
}