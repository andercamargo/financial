package com.github.andercamargo.financial.entity.operation.model;

import com.github.andercamargo.financial.entity.common.model.Common;
import com.github.andercamargo.financial.entity.operation.enumeration.OperationType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class Operation extends Common<String> {
    OperationType type;
    BigDecimal amount;
    LocalDate date;
    String walletName;
    String customerName;

    public Operation(String id, String walletName, String customerName, OperationType type, BigDecimal amount, LocalDate date) {
        super(id);
        this.walletName = walletName;
        this.customerName = customerName;
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    public Operation(String walletName, String customerName, OperationType type, BigDecimal amount, LocalDate date) {
        this.walletName = walletName;
        this.customerName = customerName;
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

}
