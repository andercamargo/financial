package com.github.andercamargo.financial.entity.wallet.exception;

public class WalletNotFoundException extends Exception {
    public WalletNotFoundException() {
        super("Wallet not found");
    }
}
