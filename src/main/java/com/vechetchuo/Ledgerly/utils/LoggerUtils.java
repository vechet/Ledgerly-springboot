package com.vechetchuo.Ledgerly.utils;

public class LoggerUtils {
    public static String formatMessage(int code, String message) {
        return String.format("ErrorCode: '%s', ErrorMessage: '%s'", code, message);
    }

    public static String formatMessage(String type, Object param) {
        return String.format("%s: %s", type, JsonConverterUtils.SerializeObject(param));
    }
}
