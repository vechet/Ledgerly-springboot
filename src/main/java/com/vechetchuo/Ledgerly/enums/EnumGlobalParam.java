package com.vechetchuo.Ledgerly.enums;

import lombok.Getter;

@Getter
public enum EnumGlobalParam {
    Deleted("Deleted"),
    Normal("Normal");

    private final String message;

    EnumGlobalParam( String message) {
        this.message = message;
    }
}
