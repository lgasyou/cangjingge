package cn.edu.bit.cangjingge.authorization.controller;

import cn.edu.bit.cangjingge.common.entity.UserAuth;
import cn.edu.bit.cangjingge.common.response.Response;
import cn.edu.bit.cangjingge.common.response.ResponseUnpacker;
import cn.edu.bit.cangjingge.common.response.ResponseUtil;
import cn.edu.bit.cangjingge.common.service.FictionService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AuthorizationController {

    @Resource
    private FictionService fictionService;

    @GetMapping("/hello")
    public Response<Integer> hello() {
        Response<Integer> response = fictionService.hello();
        Integer hello = ResponseUnpacker.unpack(response);
        return ResponseUtil.success(hello);
    }

    @ApiOperation("获得AccessToken以及RefreshToken")
    @PostMapping("/token")
    public Response<UserAuth> getToken(
            final String email,
            final String password
    ) {
        throw new NotImplementedException();
    }

    @ApiOperation("使用RefreshToken刷新令牌")
    @PutMapping("/token")
    public Response<UserAuth> updateToken() {
        throw new NotImplementedException();
    }

}
