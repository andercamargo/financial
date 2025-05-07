package com.github.andercamargo.financial.infrastructure.config.db.schema;

import com.github.andercamargo.financial.entity.wallet.model.Wallet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class WalletSchema {
    private String name;
    private BigDecimal balance;
    private Instant createdAt;

    public WalletSchema(Wallet wallet) {
        this.name = wallet.getName();
        this.balance = wallet.getBalance();
        this.createdAt = wallet.getCreatedAt();
    }

    public Wallet toWallet() {
        return new Wallet( this.getName(), this.getBalance(), this.getCreatedAt());
    }
}
