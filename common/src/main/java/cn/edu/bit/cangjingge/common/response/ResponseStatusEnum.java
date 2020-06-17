package cn.edu.bit.cangjingge.common.response;

import lombok.Getter;

public enum ResponseStatusEnum {

    // 本程序中出现的代码及其意义
    SUCCESS(200, "success"),

    USER_NOT_FOUND(400, "User not found"),
    ACCESS_DENIED(401, "Access denied"),

    INTERNAL_SERVER_ERROR(500, "Internal server error"),
    UPSTREAM_SERVER_DOWN(501, "Upstream server down");

    @Getter
    private final int status;

    @Getter
    private final String reason;

    ResponseStatusEnum(int status, String reason) {
        this.status = status;
        this.reason = reason;
    }

}
