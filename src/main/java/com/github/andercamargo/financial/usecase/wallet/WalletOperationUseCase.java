package com.github.andercamargo.financial.usecase.wallet;

import com.github.andercamargo.financial.entity.customer.model.Customer;
import com.github.andercamargo.financial.entity.operation.enumeration.OperationType;
import com.github.andercamargo.financial.entity.operation.model.Operation;
import com.github.andercamargo.financial.entity.wallet.model.Wallet;
import com.github.andercamargo.financial.infrastructure.common.validation.exception.CustomException;
import com.github.andercamargo.financial.infrastructure.customer.gateway.CustomerGateway;
import com.github.andercamargo.financial.infrastructure.operation.gateway.OperationGateway;
import com.github.andercamargo.financial.usecase.operation.dto.IOperationInputDTO;
import com.github.andercamargo.financial.usecase.operation.dto.IOperationTransferDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class WalletOperationUseCase {
    private final CustomerGateway customerGateway;
    private final OperationGateway operationGateway;

    public WalletOperationUseCase(CustomerGateway customerGateway, OperationGateway operationGateway) {
        this.customerGateway = customerGateway;
        this.operationGateway = operationGateway;
    }

    public boolean createDeposit(IOperationInputDTO operationInputDTO) throws CustomException {
        this.generateOperation(operationInputDTO, OperationType.DEPOSIT);
        var operation = new Operation(operationInputDTO.walletName(), operationInputDTO.customerName(), OperationType.DEPOSIT, operationInputDTO.amount(), LocalDate.now());
        this.operationGateway.create(operation);
        return true;
    }

    public boolean createWithdraw(IOperationInputDTO operationInputDTO) throws CustomException {
        this.generateOperation(operationInputDTO, OperationType.WITHDRAWAL);
        var operation = new Operation(operationInputDTO.walletName(), operationInputDTO.customerName(), OperationType.WITHDRAWAL, operationInputDTO.amount().multiply(BigDecimal.valueOf(-1)), LocalDate.now());
        this.operationGateway.create(operation);
        return true;
    }

    public boolean createTransfer(IOperationTransferDTO operationTransferDTO) throws CustomException {
        var customer = this.getCustomer(operationTransferDTO.customerName(), operationTransferDTO.walletNameSender());

        if(customer.getWallets().stream().filter(x-> x.getName().equals(operationTransferDTO.walletNameRecipient())).findFirst().orElse(null) == null){
            throw new CustomException(String.format("Wallet recipient %s not found for this customer", operationTransferDTO.walletNameRecipient()));
        }
        var walletSender = this.getWallet(customer, operationTransferDTO.walletNameSender());

        if(walletSender.getBalance().compareTo(operationTransferDTO.amount()) < 0){
            throw new CustomException(String.format("Insufficient balance for this wallet %s. Balance %s", operationTransferDTO.walletNameSender(), walletSender.getBalance()));
        }

        saveInitialBalance(customer.getName(), walletSender);
        saveInitialBalance(customer.getName(), this.getWallet(customer, operationTransferDTO.walletNameRecipient()));


        customer.getWallets().forEach(walletSearch -> {
            if (walletSearch.getName().equals(operationTransferDTO.walletNameSender())) {
                walletSearch.setBalance(walletSearch.getBalance().subtract(operationTransferDTO.amount()));
            }else if(walletSearch.getName().equals(operationTransferDTO.walletNameRecipient())) {
                walletSearch.setBalance(walletSearch.getBalance().add(operationTransferDTO.amount()));
            }
        });

        this.customerGateway.update(customer);

        saveOperationTransfer(operationTransferDTO.walletNameSender(), operationTransferDTO, OperationType.TRANSFER);
        saveOperationTransfer(operationTransferDTO.walletNameRecipient(), operationTransferDTO, OperationType.RECEIVE_TRANSFER);

        return true;
    }

    private void saveOperationTransfer(String walletName, IOperationTransferDTO operationTransferDTO, OperationType operationType)  {
        var operation = new Operation(walletName, operationTransferDTO.customerName(), operationType,
                operationType == OperationType.TRANSFER ?
                operationTransferDTO.amount().multiply(BigDecimal.valueOf(-1))
                : operationTransferDTO.amount()
                , LocalDate.now());
        this.operationGateway.create(operation);
    }

    public BigDecimal getBalance(LocalDate date, String customerName,   String walletName) throws CustomException {
        return this.operationGateway.getBalance(date, date, customerName, walletName);
    }

    private Customer getCustomer(String customerName, String walletName) throws CustomException {
        var customer = this.customerGateway.searchByNameAndWallet(customerName, walletName);

        if (customer == null)
            throw new CustomException(String.format("Customer or wallet not found. Wallet %s, customer %s", walletName,customerName));

        return customer;
    }

    private Wallet getWallet(Customer customer, String walletName){
        return customer.getWallets().stream().filter(x-> x.getName().equals(walletName)).findFirst().orElse(null);
    }

    private void saveInitialBalance(String customerName, Wallet wallet) throws CustomException {
        var localDate = LocalDate.now();
        var operations = this.operationGateway.findByDateAndType(localDate, localDate, OperationType.INITIAL_BALANCE.getValue());

        if (operations.isEmpty() && wallet != null) {
            this.operationGateway.create(new Operation(wallet.getName(), customerName, OperationType.INITIAL_BALANCE, wallet.getBalance(), localDate));
        }
    }


    private void generateOperation( IOperationInputDTO operationInputDTO, OperationType type) throws CustomException {
        var customer = this.getCustomer(operationInputDTO.customerName(), operationInputDTO.walletName());
        var wallet = this.getWallet(customer, operationInputDTO.walletName());
        this.saveInitialBalance(operationInputDTO.customerName(), wallet);

        customer.getWallets().forEach(walletSearch -> {
            if (walletSearch.getName().equals(operationInputDTO.walletName())) {
                walletSearch.setBalance(type == OperationType.DEPOSIT ? wallet.getBalance().add(operationInputDTO.amount()) : wallet.getBalance().subtract(operationInputDTO.amount()));
                this.customerGateway.update(customer);
            }
        });
    }
}
