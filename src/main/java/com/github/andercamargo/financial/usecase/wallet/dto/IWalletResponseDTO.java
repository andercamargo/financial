package com.github.andercamargo.financial.usecase.wallet.dto;

import jakarta.annotation.Nullable;

import java.math.BigDecimal;
import java.time.Instant;

public interface IWalletResponseDTO {
    String name();
    BigDecimal balance();
    Instant createdAt();
}
