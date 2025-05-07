package com.github.andercamargo.financial.entity.customer.gateway;

import com.github.andercamargo.financial.entity.common.gateway.ICommonGateway;
import com.github.andercamargo.financial.entity.customer.model.Customer;
import com.github.andercamargo.financial.entity.wallet.model.Wallet;

public interface ICustomerGateway extends ICommonGateway<Customer> {
    Wallet findByNameAndWallet(String customerName, String name);

    Customer searchByNameAndWallet(String customerName, String walletName);
}
