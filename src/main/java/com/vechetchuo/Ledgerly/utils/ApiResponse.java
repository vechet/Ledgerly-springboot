package com.vechetchuo.Ledgerly.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<T>(ApiResponseStatus.SUCCESS.getCode(), ApiResponseStatus.SUCCESS.getMessage(), data);
    }

    public static <T> ApiResponse<T> failure(int errorCode, String message) {
        return new ApiResponse<T>(errorCode, message, null);
    }

    public static <T> ApiResponse<T> failure(ApiResponseStatus error, String message) {
        return new ApiResponse<T>(error.getCode(), message, null);
    }

    public static <T> ApiResponse<T> failure(ApiResponseStatus error) {
        return new ApiResponse<T>(error.getCode(), error.getMessage(), null);
    }
}
