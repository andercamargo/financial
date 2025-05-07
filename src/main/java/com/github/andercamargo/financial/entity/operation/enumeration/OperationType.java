package com.github.andercamargo.financial.entity.operation.enumeration;

public enum OperationType {
    DEPOSIT("deposit"),
    WITHDRAWAL("withdrawal"),
    INITIAL_BALANCE("initial_balance"),
    TRANSFER("transfer"),
    RECEIVE_TRANSFER("receive_transfer");;

    private final String value;

    OperationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static OperationType fromValue(String value) {
        for (OperationType type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
}
