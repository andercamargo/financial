package com.github.andercamargo.financial.usecase.operation.gateway;

import com.github.andercamargo.financial.entity.operation.model.Operation;
import com.github.andercamargo.financial.infrastructure.common.validation.exception.CustomException;
import com.github.andercamargo.financial.usecase.operation.dto.IOperationInputDTO;

import java.time.LocalDate;
import java.util.List;

public interface IOperationUseCase {
    void create(IOperationInputDTO operationInputDTO) throws CustomException;
    List<Operation> findByDate(LocalDate start, LocalDate end);
    List<Operation> findByDateAndType(LocalDate start, LocalDate end, String type);
    List<Operation> findByDateAndIds(LocalDate start, LocalDate end, String walletName, String customerName);
}
