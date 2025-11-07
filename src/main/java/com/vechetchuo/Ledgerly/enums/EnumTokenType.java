package com.vechetchuo.Ledgerly.enums;

import lombok.Getter;

@Getter
public enum EnumTokenType {
    ACCESS_TOKEN(1,"access"),
    REFRESH_TOKEN(2,"refresh");

    private final int code;
    private final String message;

    EnumTokenType(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
