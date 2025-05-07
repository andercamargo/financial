package com.github.andercamargo.financial.infrastructure.config.db.schema;

import com.github.andercamargo.financial.entity.customer.model.Customer;
import com.github.andercamargo.financial.entity.wallet.model.Wallet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Document(collection = "customer")
public class CustomerSchema {
    @Id
    private String id;
    private String name;
    private List<WalletSchema> wallets;


    public CustomerSchema(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.wallets = new ArrayList<>();
        customer.getWallets().forEach(wallet -> this.wallets.add(new WalletSchema(wallet)));
    }

    public Customer toCustomer() {
        var wallets = new ArrayList<Wallet>();
        this.getWallets().forEach(walletSchema -> wallets.add(walletSchema.toWallet()));
        return new Customer(this.getId(), this.getName(), wallets);
    }
}
