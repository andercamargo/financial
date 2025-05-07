package com.github.andercamargo.financial.entity.customer.model;

import com.github.andercamargo.financial.entity.common.model.Common;
import com.github.andercamargo.financial.entity.wallet.model.Wallet;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@Setter
public class Customer extends Common<String> {
    private String name;
    private List<Wallet> wallets;

    public Customer(String name, List<Wallet> wallets) {
        this.name = name;
        this.wallets = wallets;
    }

    public Customer(String uuid, String name, List<Wallet> wallets) {
        super(uuid);
        this.name = name;
        this.wallets = wallets;
    }

}