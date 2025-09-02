package com.vechetchuo.Ledgerly.enums;

import lombok.Getter;

@Getter
public enum EnumRoles
{
    ROLE_SYSTEM_ADMIN(1,"ROLE_SYSTEM_ADMIN"),
    ROLE_ADMIN(2,"ROLE_ADMIN"),
    ROLE_USER(3,"ROLE_USER");

    private final int code;
    private final String message;

    EnumRoles(int code, String message) {
        this.code = code;
        this.message = message;
    }
}