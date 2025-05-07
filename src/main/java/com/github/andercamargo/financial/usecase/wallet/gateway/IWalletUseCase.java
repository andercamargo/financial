package com.github.andercamargo.financial.usecase.wallet.gateway;

import com.github.andercamargo.financial.entity.wallet.model.Wallet;
import com.github.andercamargo.financial.infrastructure.common.validation.exception.CustomException;
import com.github.andercamargo.financial.usecase.wallet.dto.IWalletInputDTO;

public interface IWalletUseCase {
    Wallet create(IWalletInputDTO walletInputDTO) throws CustomException;
    Wallet retrieveBalance(String customerName, String walletName) throws CustomException;

}