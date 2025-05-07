package com.github.andercamargo.financial.infrastructure.operation.dto;

import com.github.andercamargo.financial.infrastructure.common.validation.formats.Name;
import com.github.andercamargo.financial.usecase.operation.dto.IOperationTransferDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public record OperationTransferInputDTO(
        @Name(message = "{wallet.name.sender}")
        String walletNameSender,
        @Name(message = "{wallet.name.recipient}")
        String walletNameRecipient,
        @Name(message = "{customer.name}")
        String customerName,
        @Min(0)
        @Max(value = 10000000, message = "{operation.amount}")
        BigDecimal amount
)implements IOperationTransferDTO { }
