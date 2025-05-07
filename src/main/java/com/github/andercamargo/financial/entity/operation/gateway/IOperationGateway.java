package com.github.andercamargo.financial.entity.operation.gateway;

import com.github.andercamargo.financial.entity.common.gateway.ICommonGateway;
import com.github.andercamargo.financial.entity.operation.model.Operation;

import java.time.LocalDate;
import java.util.List;

public interface IOperationGateway extends ICommonGateway<Operation> {
    List<Operation> findByDateAndType(LocalDate start, LocalDate end, String type);
    List<Operation> findByDate(LocalDate start, LocalDate end);
}
