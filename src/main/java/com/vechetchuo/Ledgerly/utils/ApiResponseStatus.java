package com.vechetchuo.Ledgerly.utils;

import lombok.Getter;

@Getter
public enum ApiResponseStatus {
    INTERNAL_ERROR(500, "Internal Error"),
    BAD_REQUEST(400, "Bad Request"),
    SUCCESS(0, "Success"),
    NOT_FOUND(404, "Not Found"),
    UNAUTHORIZED(401, "Unauthorized"),
    ALREADY_DELETE(422, "Cannot update or delete record that already deleted.");

    private final int code;
    private final String message;

    ApiResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
