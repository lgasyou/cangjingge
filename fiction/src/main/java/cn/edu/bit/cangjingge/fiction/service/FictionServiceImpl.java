package cn.edu.bit.cangjingge.fiction.service;

import cn.edu.bit.cangjingge.common.exception.BusinessException;
import cn.edu.bit.cangjingge.common.response.ResultStatusEnum;
import org.springframework.stereotype.Service;

@Service
public class FictionServiceImpl {

    public Integer hello() {
        throw new BusinessException(ResultStatusEnum.USER_NOT_FOUND);
    }

}
