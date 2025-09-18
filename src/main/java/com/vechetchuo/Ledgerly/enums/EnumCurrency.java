package com.vechetchuo.Ledgerly.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum EnumCurrency {
    USD("USD"),
    KH("KH");

    private final String message;

    EnumCurrency( String message) {
        this.message = message;
    }

    public static boolean isValid(String input) {
        return Arrays.stream(values())
                .anyMatch(e -> e.getMessage().equalsIgnoreCase(input));
    }
}
