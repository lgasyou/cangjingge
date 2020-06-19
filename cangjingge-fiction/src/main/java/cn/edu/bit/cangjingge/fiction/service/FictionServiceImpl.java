package cn.edu.bit.cangjingge.fiction.service;

import cn.edu.bit.cangjingge.common.exception.BusinessException;
import cn.edu.bit.cangjingge.common.response.ResponseStatusEnum;
import org.springframework.stereotype.Service;

@Service
public class FictionServiceImpl {

    public Integer hello() {
        throw new BusinessException(ResponseStatusEnum.USER_NOT_FOUND);
    }

}
