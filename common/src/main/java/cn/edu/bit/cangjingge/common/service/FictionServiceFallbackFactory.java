package cn.edu.bit.cangjingge.common.service;

import cn.edu.bit.cangjingge.common.exception.BusinessException;
import cn.edu.bit.cangjingge.common.response.ResultStatusEnum;
import cn.edu.bit.cangjingge.common.response.Result;
import feign.hystrix.FallbackFactory;

// Hystrix 熔断
public class FictionServiceFallbackFactory implements FallbackFactory<FictionService> {

    @Override
    public FictionService create(Throwable throwable) {
        return new FictionService() {
            @Override
            public Result<Integer> hello() {
                throw new BusinessException(ResultStatusEnum.UPSTREAM_SERVER_DOWN);
            }

            @Override
            public Result<String> helloError() {
                throw new BusinessException(ResultStatusEnum.UPSTREAM_SERVER_DOWN);
            }
        };
    }

}
