package cn.edu.bit.cangjingge.common.response;

import lombok.Getter;

// 本程序中的状态码及其意义
public enum ResponseStatusEnum {

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
    INCORRECT_USERNAME_OR_PASSWORD(2004, "Incorrect username or password"),
    USER_ALREADY_EXISTED(2004, "User already existed"),

    //Bookshelf
    BOOKSHELF_NOT_FOUND(3001,"Bookshelf not found"),
    BOOKSHELF_CREATION_FAILURE(3002,"Bookshelf create failure"),

    SMS_CODE_INVALID(4001, "SMS code invalid"),
    MOBILE_NUMBER_ILLEGAL(4002, "mobile number illegal"),
    TOO_FREQUENTLY_REQUEST(4003, "Too frequently request");

    @Getter
    private final int status;

    @Getter
    private final String reason;

    ResponseStatusEnum(int status, String reason) {
        this.status = status;
        this.reason = reason;
    }

}
