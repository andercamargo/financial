package com.github.andercamargo.financial.infrastructure.customer.gateway;

import com.github.andercamargo.financial.entity.customer.gateway.ICustomerGateway;
import com.github.andercamargo.financial.entity.customer.model.Customer;
import com.github.andercamargo.financial.entity.wallet.model.Wallet;
import com.github.andercamargo.financial.infrastructure.config.db.repository.CustomerRepository;
import com.github.andercamargo.financial.infrastructure.config.db.schema.CustomerSchema;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Objects;


public class CustomerGateway implements ICustomerGateway {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerGateway(CustomerRepository repository) {
        this.customerRepository = repository;
    }

    @Override
    public Customer create(Customer customer) {
        var customerSchema = this.customerRepository.findByUserName(customer.getName());
        if (customerSchema != null) {
            var wallets = new ArrayList<Wallet>(customerSchema.toCustomer().getWallets());
            wallets.add(customer.getWallets().getFirst());
            customer.setWallets(wallets);
            customer.setId(customerSchema.getId());
        }
        return this.customerRepository.save(new CustomerSchema(customer)).toCustomer();
    }

    @Override
    public Customer update(Customer customer) {
        return this.customerRepository.save(new CustomerSchema(customer)).toCustomer();
    }

    @Override
    public Wallet findByNameAndWallet(String customerName, String name) {
        var customer = this.customerRepository.findByUserNameAndWallet(customerName, name);

        if (customer == null || customer.getWallets() == null) {
            return null;
        }

        return Objects.requireNonNull(customer.getWallets().stream()
                .filter(x -> x.getName().equals(name)).findFirst().orElse(null)).toWallet();
    }

    @Override
    public Customer searchByNameAndWallet(String customerName, String name) {
        var customer = this.customerRepository.findByUserNameAndWallet(customerName, name);

        if (customer == null) {
            return null;
        }
        return customer.toCustomer();
    }
}
