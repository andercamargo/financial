package com.github.andercamargo.financial.usecase.wallet;

import com.github.andercamargo.financial.entity.customer.model.Customer;
import com.github.andercamargo.financial.entity.wallet.model.Wallet;
import com.github.andercamargo.financial.infrastructure.common.validation.exception.CustomException;
import com.github.andercamargo.financial.infrastructure.customer.gateway.CustomerGateway;
import com.github.andercamargo.financial.infrastructure.wallet.dto.WalletInputDTO;
import com.github.andercamargo.financial.infrastructure.wallet.validation.UniqueWalletValidator;
import com.github.andercamargo.financial.usecase.wallet.dto.IWalletInputDTO;
import com.github.andercamargo.financial.usecase.wallet.gateway.IWalletUseCase;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;

public class WalletUseCase implements IWalletUseCase {
    private final CustomerGateway customerGateway;

    @Autowired
    private UniqueWalletValidator validator;

    public WalletUseCase(CustomerGateway customerGateway) {
        this.customerGateway = customerGateway;
    }

    private void valid(IWalletInputDTO walletInputDTO) throws CustomException{
        if(!validator.isValid((WalletInputDTO) walletInputDTO, null)){
            throw new CustomException("Wallet already exists for this customer");
        }
    }


    @Override
    public Wallet create(IWalletInputDTO walletInputDTO) throws CustomException {
        valid(walletInputDTO);

        var wallets = new ArrayList<Wallet>();
        wallets.add(new Wallet(walletInputDTO.name(), BigDecimal.ZERO, Instant.now()));

        Customer customer = new Customer(walletInputDTO.customerName(), wallets);

        return this.customerGateway.create(customer).getWallets().getFirst();
    }

    @Override
    public Wallet retrieveBalance(String customerName, String walletName) throws CustomException {
        return this.customerGateway.findByNameAndWallet(customerName, walletName);
    }
}
