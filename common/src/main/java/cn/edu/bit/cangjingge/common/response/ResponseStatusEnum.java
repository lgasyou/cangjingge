package cn.edu.bit.cangjingge.common.response;

import lombok.Getter;

public enum ResponseStatusEnum {

    // 本程序中出现的代码及其意义
    SUCCESS(200, "success"),

    ACCESS_DENIED(401, "Access denied"),

    INTERNAL_SERVER_ERROR(500, "Internal server error"),
    UPSTREAM_SERVER_DOWN(501, "Upstream server down"),

    USER_NOT_FOUND(1000, "User not found"),
    FICTION_NOT_FOUND(1001, "Fiction not found"),
    FICTION_CHAPTER_NOT_FOUND(1002, "Fiction chapter not found"),
    FICTION_REVIEW_NOT_FOUND(1003, "Fiction review not found");

    @Getter
    private final int status;

    @Getter
    private final String reason;

    ResponseStatusEnum(int status, String reason) {
        this.status = status;
        this.reason = reason;
    }

}
