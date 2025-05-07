package com.github.andercamargo.financial.infrastructure.config.db.schema;

import com.github.andercamargo.financial.entity.operation.enumeration.OperationType;
import com.github.andercamargo.financial.entity.operation.model.Operation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@Document(collection = "operation")
public class OperationSchema {
    @Id private String id;
    private String walletName;
    private String customerName;
    private String type;
    private BigDecimal amount;
    private LocalDate date;

    public OperationSchema(Operation operation) {
        this.id = operation.getId();
        this.type = operation.getType().getValue();
        this.walletName = operation.getWalletName();
        this.customerName = operation.getCustomerName();
        this.amount = operation.getAmount();
        this.date = operation.getDate();
    }

    public Operation toOperation() {
        return new Operation(this.getId(),this.getWalletName(), this.getCustomerName(), OperationType.fromValue(this.getType()), this.getAmount(), this.getDate());
    }
}
