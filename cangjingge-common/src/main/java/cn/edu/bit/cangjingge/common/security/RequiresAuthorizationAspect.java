package cn.edu.bit.cangjingge.common.security;

import cn.edu.bit.cangjingge.common.exception.BusinessException;
import cn.edu.bit.cangjingge.common.response.Response;
import cn.edu.bit.cangjingge.common.response.ResponseStatusEnum;
import cn.edu.bit.cangjingge.common.response.ResponseUnpacker;
import cn.edu.bit.cangjingge.common.service.AuthorizationService;
import cn.edu.bit.cangjingge.common.util.TokenUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
@Component
public class RequiresAuthorizationAspect {

    @Resource
    private AuthorizationService authService;

    // 使用headers中的信息进行鉴权
    @Before("@annotation(cn.edu.bit.cangjingge.common.security.RequiresAuthorization)")
    public void doCheck(JoinPoint joinPoint) throws BusinessException {
        String token = TokenUtil.getTokenFromHeaders();
        if (token == null) {
            throw new BusinessException(ResponseStatusEnum.TOKEN_INVALID);
        }
        Response<String> response = authService.checkToken(token);
        ResponseUnpacker.unpack(response);

        String[] requiredRoles = getJoinPointAnnotation(joinPoint, RequiresAuthorization.class).value();
        String[] roles = TokenUtil.getUserRoles(token);
        checkAccess(requiredRoles, roles);
    }

    private static void checkAccess(
            final String[] requiredRoles,
            final String[] roles
    ) throws BusinessException {
        boolean fullfilled;
        for (String rr : requiredRoles) {
            fullfilled = false;
            for (String r : roles) {
                if (rr.equals(r)) {
                    fullfilled = true;
                    break;
                }
            }
            if (!fullfilled) {
                throw new BusinessException(ResponseStatusEnum.ACCESS_DENIED);
            }
        }
    }

    private static <T extends Annotation> T getJoinPointAnnotation(JoinPoint joinPoint, Class<T> annotationClass) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        return targetMethod.getAnnotation(annotationClass);
    }

}
