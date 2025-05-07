package com.github.andercamargo.financial.infrastructure.config.web;

import com.github.andercamargo.financial.infrastructure.config.db.repository.CustomerRepository;
import com.github.andercamargo.financial.infrastructure.config.db.repository.OperationRepository;
import com.github.andercamargo.financial.infrastructure.customer.gateway.CustomerGateway;
import com.github.andercamargo.financial.infrastructure.operation.gateway.OperationGateway;
import com.github.andercamargo.financial.usecase.wallet.WalletOperationUseCase;
import com.github.andercamargo.financial.usecase.wallet.WalletUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ResourceBundle;

@Configuration
public class WebConfig {
    @Bean
    public ResourceBundle messageBundle() {
        return ResourceBundle.getBundle("ValidationMessages");
    }

    @Bean
    public WalletUseCase createCustomerUseCase(CustomerRepository customerRepository) {
        CustomerGateway customerGateway = new CustomerGateway(customerRepository);
        return new WalletUseCase(customerGateway);
    }


    @Bean
    public WalletOperationUseCase createWalletOperationUseCase(CustomerRepository customerRepository, OperationRepository operationRepository) {
        CustomerGateway customerGateway = new CustomerGateway(customerRepository);
        OperationGateway operationGateway = new OperationGateway(operationRepository);
        return new WalletOperationUseCase(customerGateway, operationGateway);
    }
}
