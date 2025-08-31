package com.vechetchuo.Ledgerly.utils;

public class LoggerUtil {
    public static String formatMessage(int code, String message) {
        return String.format("ErrorCode: '%s', ErrorMessage: '%s'", code, message);
    }

    public static String formatMessage(String type, Object param) {
        return String.format("%s: %s", type, JsonConverterUtils.SerializeObject(param));
    }

    public static String formatMessage(Object param, int code, String msg){
        return String.format("Param: %s, ErrorCode: '%s', ErrorMessage: '%s'", JsonConverterUtils.SerializeObject(param), code, msg);
    }

    public static String formatMessage(Object param, ApiResponseStatus status){
        return String.format("Param: %s, ErrorCode: '%s', ErrorMessage: '%s'", JsonConverterUtils.SerializeObject(param), status.getCode(), status.getMessage());
    }
}
