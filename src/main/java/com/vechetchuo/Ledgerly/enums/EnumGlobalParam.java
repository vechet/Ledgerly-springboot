package com.vechetchuo.Ledgerly.enums;

import lombok.Getter;

@Getter
public enum EnumGlobalParam {
    Deleted("Normal"),
    Normal("Normal");

    private final String message;

    EnumGlobalParam( String message) {
        this.message = message;
    }
}
