package cn.edu.bit.cangjingge.common.exception;

import cn.edu.bit.cangjingge.common.response.ResponseStatusEnum;
import lombok.Getter;

public class BusinessException extends RuntimeException {

    @Getter
    private final int status;

    public BusinessException(int status, String message) {
        super(message);
        this.status = status;
    }

    public BusinessException(ResponseStatusEnum exceptionEnum) {
        this(exceptionEnum.getStatus(), exceptionEnum.getReason());
    }

}
