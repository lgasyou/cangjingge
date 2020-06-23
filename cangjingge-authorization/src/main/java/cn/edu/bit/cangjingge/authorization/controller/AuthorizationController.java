package cn.edu.bit.cangjingge.authorization.controller;

import cn.edu.bit.cangjingge.authorization.entity.UserAuthInfo;
import cn.edu.bit.cangjingge.authorization.service.AuthorizationServiceImpl;
import cn.edu.bit.cangjingge.common.response.Response;
import cn.edu.bit.cangjingge.common.response.ResponseStatusEnum;
import cn.edu.bit.cangjingge.common.response.ResponseUtil;
import cn.edu.bit.cangjingge.common.security.RequiresAuthorization;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AuthorizationController {

    @Resource
    AuthorizationServiceImpl authorizationService;

    @GetMapping("/hello")
    @RequiresAuthorization
    public Response<String> hello() {
        return ResponseUtil.success("Hello, world");
    }

    @GetMapping("/add")
    public Response<String> add() {
        authorizationService.createUserAuth("xenon", "123");
        return ResponseUtil.success();
    }

    @ApiOperation("获得AccessToken")
    @PostMapping("/token")
    public Response<UserAuthInfo> getToken(
            final String username,
            final String password
    ) {
        UserAuthInfo userAuthInfo = authorizationService.loginWithPassword(username, password);
        return ResponseUtil.success(userAuthInfo);
    }

    @ApiOperation("刷新令牌（需要认证）")
    @PutMapping("/token")
    @RequiresAuthorization
    public Response<UserAuthInfo> updateToken() {
        UserAuthInfo userAuthInfo = authorizationService.refreshToken();
        return ResponseUtil.success(userAuthInfo);
    }

    @ApiOperation("检查Token是否有效")
    @GetMapping("/token/validation")
    public Response<String> checkToken(
            final String token
    ) {
        authorizationService.checkToken(token);
        return ResponseUtil.success(ResponseStatusEnum.TOKEN_VALID.getReason());
    }

}
