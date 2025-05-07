package com.github.andercamargo.financial.infrastructure.operation.gateway;

import com.github.andercamargo.financial.entity.operation.gateway.IOperationGateway;
import com.github.andercamargo.financial.entity.operation.model.Operation;
import com.github.andercamargo.financial.infrastructure.config.db.repository.OperationRepository;
import com.github.andercamargo.financial.infrastructure.config.db.schema.OperationSchema;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class OperationGateway implements IOperationGateway {
    private final OperationRepository operationRepository;

    @Autowired
    public OperationGateway(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public List<Operation> findByDate(LocalDate start, LocalDate end) {
        var operations = this.operationRepository.findByDate(start, end);
        return operations.stream().map(OperationSchema::toOperation).toList();
    }

    @Override
    public List<Operation> findByDateAndType(LocalDate start, LocalDate end, String type) {
        var operations = this.operationRepository.findByDateAndType(start, end, type);
        return operations.stream().map(OperationSchema::toOperation).toList();
    }

    public BigDecimal getBalance(LocalDate startDate, LocalDate endDate, String walletName, String customerName) {
        //var balance = this.operationRepository.getBalance(startDate, endDate, walletName, customerName);
        var operations = this.operationRepository.findByDateAndIds(startDate, endDate, walletName, customerName);
        final BigDecimal[] value = {BigDecimal.ZERO};

        operations.parallelStream().forEachOrdered(x-> {
            value[0] = value[0].add(x.getAmount());
        });

        return value[0];
    }

    @Override
    public Operation create(Operation entity) {
        return this.operationRepository.save(new OperationSchema(entity)).toOperation();
    }

    @Override
    public Operation update(Operation entity) {
        return this.operationRepository.save(new OperationSchema(entity)).toOperation();
    }
}
