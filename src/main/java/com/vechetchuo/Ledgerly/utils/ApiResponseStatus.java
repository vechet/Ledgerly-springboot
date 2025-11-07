package com.vechetchuo.Ledgerly.utils;

import lombok.Getter;

@Getter
public enum ApiResponseStatus {
    INTERNAL_ERROR(500, "Something went wrong"),
    BAD_REQUEST(400, "Bad Request"),
    SUCCESS(0, "Success"),
    NOT_FOUND(404, "Not Found"),
    EMAIL_NOT_FOUND(404, "Email not found"),
    USER_NOT_FOUND(404, "User not found"),
    UNAUTHORIZED(401, "Unauthorized"),
    INVALID_TOKEN_TYPE(404, "Invalid token type"),
    INVALID_OR_EXPIRED_REFRESH_TOKEN(404, "Invalid or expired refresh token"),
    DUPLICATION_USERNAME(409, "Duplicate username"),
    DUPLICATION_EMAIL(410, "Duplicate email"),
    FORBIDDEN(403, "Forbidden"),
    CANNOT_UPDATE_DEFAULT_CATEGORY(1002, "Cannot update default category"),
    ALREADY_DELETE(422, "Cannot update or delete record that already deleted."),
    CANNOT_DELETE_DEFAULT_CATEGORY(423, "Cannot delete default category"),
    ACCOUNT_ALREADY_DELETED(1000, "This account is not exits"),
    INVALID_CURRENCY(1003, "Invalid currency"),
    INVALID_TYPE(1004, "Invalid type"),
    LINK_EXPIRED(1005, "Link expired"),
    INVALID_LINK(1006, "Invalid link"),
    CURRENT_PASSWORD_INCORRECT(1007, "Current password is incorrect"),
    CATEGORY_ALREADY_DELETED(1001, "This category is  not exits");

    private final int code;
    private final String message;

    ApiResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
