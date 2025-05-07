package com.github.andercamargo.financial.infrastructure.wallet.dto;

import com.github.andercamargo.financial.entity.wallet.model.Wallet;
import com.github.andercamargo.financial.usecase.wallet.dto.IWalletResponseDTO;
import jakarta.annotation.Nullable;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public record WalletResponseDTO(String name, BigDecimal balance, Instant createdAt) implements IWalletResponseDTO {
    public WalletResponseDTO(Wallet wallet) {
            this(wallet.getName(),
                    wallet.getBalance(),
                    wallet.getCreatedAt());
    }
}
