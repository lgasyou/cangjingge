package cn.edu.bit.cangjingge.common.response;

import cn.edu.bit.cangjingge.common.exception.BusinessException;

public class ResultUnpacker {

    public static <T> T unpack(Result<T> response) {
        if (response.getStatus() != ResultStatusEnum.SUCCESS.getStatus()) {
            throw new BusinessException(response.getStatus(), response.getReason());
        }
        return response.getData();
    }

}
