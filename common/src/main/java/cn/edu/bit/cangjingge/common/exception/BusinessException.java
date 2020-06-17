package cn.edu.bit.cangjingge.common.exception;

import cn.edu.bit.cangjingge.common.response.ResponseStatusEnum;
import lombok.Getter;

public class BusinessException extends RuntimeException {

    @Getter
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ResponseStatusEnum exceptionEnum) {
        this(exceptionEnum.getStatus(), exceptionEnum.getReason());
    }

}
