package com.github.andercamargo.financial.infrastructure.wallet.dto;

import com.github.andercamargo.financial.infrastructure.common.validation.formats.Name;
import com.github.andercamargo.financial.usecase.wallet.dto.IWalletInputDTO;


public record WalletInputDTO(
        @Name(message = "{wallet.name}")
        String name,
        @Name(message = "{customer.name}")
        String customerName
      ) implements IWalletInputDTO {
}