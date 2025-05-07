package com.github.andercamargo.financial.usecase.operation.dto;


import java.math.BigDecimal;

public interface IOperationTransferDTO {
    String walletNameSender();
    String walletNameRecipient();
    String customerName();
    BigDecimal amount();
}
