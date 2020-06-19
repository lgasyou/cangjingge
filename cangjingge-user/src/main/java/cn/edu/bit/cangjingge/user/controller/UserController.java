package cn.edu.bit.cangjingge.user.controller;

import cn.edu.bit.cangjingge.common.entity.User;
import cn.edu.bit.cangjingge.common.response.Response;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @ApiOperation("使用邮箱及密码进行注册")
    @PostMapping("/")
    public Response<User> register(
            final String email,
            final String password
    ) {
        throw new NotImplementedException();
    }

    @ApiOperation("使用用户ID获取用户基本信息")
    @GetMapping("/{id}")
    public Response<User> getById(
            @PathVariable("id") final Long id
    ) {
        throw new NotImplementedException();
    }

}
