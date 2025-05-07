package com.github.andercamargo.financial.usecase.operation.dto;


import java.math.BigDecimal;

public interface IOperationInputDTO {
    String walletName();
    String customerName();
    BigDecimal amount();
}
