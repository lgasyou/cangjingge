package cn.edu.bit.cangjingge.common.response;

import lombok.Getter;

public enum ResponseStatusEnum {

    // 本程序中出现的代码及其意义
    SUCCESS(200, "success"),

    ACCESS_DENIED(401, "Access denied"),

    INTERNAL_SERVER_ERROR(500, "Internal server error"),
    UPSTREAM_SERVER_DOWN(501, "Upstream server down"),

    // Fiction
    FICTION_NOT_FOUND(1001, "Fiction not found"),
    FICTION_CHAPTER_NOT_FOUND(1002, "Fiction chapter not found"),
    FICTION_REVIEW_NOT_FOUND(1003, "Fiction review not found"),
    FICTION_CREATION_FAILURE(1004, "Fiction create failure"),
    FICTION_CHAPTER_CREATION_FAILURE(1005, "Fiction chapter create failure"),
    FICTION_REVIEW_CREATION_FAILURE(1006, "Fiction review create failure"),

    // User
    USER_NOT_FOUND(2001, "User not found"),
    TOKEN_VALID(2002, "Token is valid"),
    TOKEN_INVALID(2003, "Token is invalid"),
    INCORRECT_USERNAME_OR_PASSWORD(2004, "Incorrect username or password");

    @Getter
    private final int status;

    @Getter
    private final String reason;

    ResponseStatusEnum(int status, String reason) {
        this.status = status;
        this.reason = reason;
    }

}
