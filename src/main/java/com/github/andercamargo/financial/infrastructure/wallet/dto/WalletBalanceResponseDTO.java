package com.github.andercamargo.financial.infrastructure.wallet.dto;

import com.github.andercamargo.financial.entity.wallet.model.Wallet;
import com.github.andercamargo.financial.usecase.wallet.dto.IWalletBalanceResponseDTO;
import com.github.andercamargo.financial.usecase.wallet.dto.IWalletResponseDTO;

import java.math.BigDecimal;
import java.time.Instant;

public record WalletBalanceResponseDTO(BigDecimal balance) implements IWalletBalanceResponseDTO {
    public WalletBalanceResponseDTO(Wallet wallet) {
            this(wallet.getBalance());
    }
}
