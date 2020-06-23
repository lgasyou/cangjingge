package cn.edu.bit.cangjingge.common.security;

import cn.edu.bit.cangjingge.common.exception.BusinessException;
import cn.edu.bit.cangjingge.common.response.Response;
import cn.edu.bit.cangjingge.common.response.ResponseStatusEnum;
import cn.edu.bit.cangjingge.common.response.ResponseUnpacker;
import cn.edu.bit.cangjingge.common.service.AuthorizationService;
import cn.edu.bit.cangjingge.common.util.TokenUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Aspect
@Component
public class RequiresAuthorizationAspect {

    @Resource
    private AuthorizationService authService;

    // 使用headers中的信息进行鉴权
    @Before("@annotation(cn.edu.bit.cangjingge.common.security.RequiresAuthorization)")
    public void doCheck() throws BusinessException {
        String token = TokenUtil.getTokenFromHeaders();
        if (token == null) {
            throw new BusinessException(ResponseStatusEnum.TOKEN_INVALID);
        }
        Response<String> response = authService.checkToken(token);
        ResponseUnpacker.unpack(response);
    }

}
