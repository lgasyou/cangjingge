package cn.edu.bit.cangjingge.authorization.controller;

import cn.edu.bit.cangjingge.authorization.entity.UserAuthInfo;
import cn.edu.bit.cangjingge.authorization.service.AliyunOssServiceImpl;
import cn.edu.bit.cangjingge.authorization.service.AuthorizationServiceImpl;
import cn.edu.bit.cangjingge.common.dto.AliyunOssAuthorization;
import cn.edu.bit.cangjingge.common.response.Response;
import cn.edu.bit.cangjingge.common.response.ResponseStatusEnum;
import cn.edu.bit.cangjingge.common.response.ResponseUtil;
import cn.edu.bit.cangjingge.common.security.RequiresAuthorization;
import com.aliyuncs.exceptions.ClientException;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class AuthorizationController {

    @Resource
    AuthorizationServiceImpl authorizationService;

    @Resource
    AliyunOssServiceImpl ossService;

    @ApiOperation("获取访问图片的授权信息（需要认证，用户权限及以上）")
    @GetMapping("/token/image")
    @RequiresAuthorization
    public Response<AliyunOssAuthorization> getImageAuthorization() throws ClientException {
        AliyunOssAuthorization authorization = ossService.getOssAuthorization();
        return ResponseUtil.success(authorization);
    }

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

    @ApiOperation("重置密码（需要认证，用户权限及以上）")
    @PutMapping("/password")
    @RequiresAuthorization
    public Response<String> resetPassword(
            @RequestParam("password") String newPassword
    ) {
        authorizationService.resetPassword(newPassword);
        return ResponseUtil.success();
    }

    @ApiOperation("使用用户名及密码创建安全信息（内部接口，外部服务无法使用）")
    @PostMapping("/")
    public Response<String> register(
            @RequestParam("username") final String username,
            @RequestParam("password") final String password
    ) {
        authorizationService.createUserAuth(username, password);
        return ResponseUtil.success();
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
