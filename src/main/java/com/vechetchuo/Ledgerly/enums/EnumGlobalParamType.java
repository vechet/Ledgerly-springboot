package com.vechetchuo.Ledgerly.enums;

import lombok.Getter;

@Getter
public enum EnumGlobalParamType {
    AccountxxxStatus("AccountxxxStatus"),
    CategoryxxxStatus("CategoryxxxStatus"),
    TransactionxxxStatus("TransactionxxxStatus");

    private final String message;

    EnumGlobalParamType(String message) {
        this.message = message;
    }
}
