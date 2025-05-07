package com.github.andercamargo.financial.entity.wallet.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class Wallet {
    private String name;
    private BigDecimal balance;
    private Instant createdAt;

    public Wallet(String name, BigDecimal balance, Instant createdAt) {
        this.name = name;
        this.balance = balance;
        this.createdAt = createdAt;
    }
}