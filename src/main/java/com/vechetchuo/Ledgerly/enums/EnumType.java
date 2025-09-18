package com.vechetchuo.Ledgerly.enums;


import lombok.Getter;

import java.util.Arrays;

@Getter
public enum EnumType {
    INCOME("INCOME"),
    EXPENSE("EXPENSE");

    private final String message;

    EnumType( String message) {
        this.message = message;
    }

    public static boolean isValid(String input) {
        return Arrays.stream(values())
                .anyMatch(e -> e.getMessage().equalsIgnoreCase(input));
    }
}
