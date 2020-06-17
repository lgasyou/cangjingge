package cn.edu.bit.cangjingge.common.response;

import cn.edu.bit.cangjingge.common.exception.BusinessException;

public class ResponseUnpacker {

    public static <T> T unpack(Response<T> response) throws BusinessException {
        if (response.getStatus() != ResponseStatusEnum.SUCCESS.getStatus()) {
            throw new BusinessException(response.getStatus(), response.getReason());
        }
        return response.getData();
    }

}
