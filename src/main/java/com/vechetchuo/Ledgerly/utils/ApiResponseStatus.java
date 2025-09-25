package com.vechetchuo.Ledgerly.utils;

import lombok.Getter;

@Getter
public enum ApiResponseStatus {
    INTERNAL_ERROR(500, "Internal Error"),
    BAD_REQUEST(400, "Bad Request"),
    SUCCESS(0, "Success"),
    NOT_FOUND(404, "Not Found"),
    UNAUTHORIZED(401, "Unauthorized"),
    DUPLICATION_USERNAME(409, "Duplicate username"),
    FORBIDDEN(403, "Forbidden"),
    CANNOT_UPDATE_DEFAULT_CATEGORY(1002, "Cannot update default category"),
    ALREADY_DELETE(422, "Cannot update or delete record that already deleted."),
    CANNOT_DELETE_DEFAULT_CATEGORY(423, "Cannot delete default category"),
    ACCOUNT_ALREADY_DELETED(1000, "This account is not exits"),
    INVALID_CURRENCY(1003, "Invalid currency"),
    INVALID_TYPE(1004, "Invalid type"),
    LINK_EXPIRED(1005, "Link expired"),
    CATEGORY_ALREADY_DELETED(1001, "This category is  not exits");

    private final int code;
    private final String message;

    ApiResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
