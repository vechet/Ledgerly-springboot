package com.vechetchuo.Ledgerly.enums;

import lombok.Getter;

@Getter
public enum EnumPermissions {
    ACCOUNT_CREATE(1,"ACCOUNT_CREATE"),
    ACCOUNT_UPDATE(2,"ACCOUNT_UPDATE"),
    ACCOUNT_DELETE(3,"ACCOUNT_DELETE"),
    ACCOUNT_VIEW(4,"ACCOUNT_VIEW"),
    CATEGORY_CREATE(5,"CATEGORY_CREATE"),
    CATEGORY_UPDATE(6,"CATEGORY_UPDATE"),
    CATEGORY_DELETE(7,"CATEGORY_DELETE"),
    CATEGORY_VIEW(8,"CATEGORY_VIEW"),
    TRANSACTION_CREATE(9,"TRANSACTION_CREATE"),
    TRANSACTION_UPDATE(10,"TRANSACTION_UPDATE"),
    TRANSACTION_DELETE(11,"TRANSACTION_DELETE"),
    TRANSACTION_VIEW(12,"TRANSACTION_VIEW"),
    USER_CHANGE_PASSWORD(13, "USER_CHANGE_PASSWORD"),
    USER_INFO(14, "USER_INFO");

    private final int code;
    private final String message;

    EnumPermissions(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
