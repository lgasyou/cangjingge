package cn.edu.bit.cangjingge.authorization.controller;

import cn.edu.bit.cangjingge.authorization.entity.UserAuthInfo;
import cn.edu.bit.cangjingge.authorization.service.AuthorizationServiceImpl;
import cn.edu.bit.cangjingge.common.response.Response;
import cn.edu.bit.cangjingge.common.response.ResponseStatusEnum;
import cn.edu.bit.cangjingge.common.response.ResponseUtil;
import cn.edu.bit.cangjingge.common.security.RequiresAuthorization;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class AuthorizationController {

    @Resource
    AuthorizationServiceImpl authorizationService;

    @ApiOperation("获得授权信息")
    @PostMapping("/token")
    public Response<UserAuthInfo> getToken(
            @RequestParam("username") final String username,
            @RequestParam("password") final String password
    ) {
        UserAuthInfo userAuthInfo = authorizationService.loginWithPassword(username, password);
        return ResponseUtil.success(userAuthInfo);
    }

    @ApiOperation("刷新令牌（需要认证，用户权限及以上）")
    @PutMapping("/token")
    @RequiresAuthorization
    public Response<UserAuthInfo> updateToken() {
        UserAuthInfo userAuthInfo = authorizationService.refreshToken();
        return ResponseUtil.success(userAuthInfo);
    }

    @ApiOperation("检查Token是否有效（内部接口，外部服务无法使用）")
    @GetMapping("/token/validation")
    public Response<String> checkToken(
            @RequestParam("token") final String token
    ) {
        authorizationService.checkToken(token);
        return ResponseUtil.success(ResponseStatusEnum.TOKEN_VALID.getReason());
    }

}
