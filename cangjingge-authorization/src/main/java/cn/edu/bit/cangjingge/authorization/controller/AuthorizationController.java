package cn.edu.bit.cangjingge.authorization.controller;

import cn.edu.bit.cangjingge.common.response.Response;
import cn.edu.bit.cangjingge.common.response.ResponseUnpacker;
import cn.edu.bit.cangjingge.common.response.ResponseUtil;
import cn.edu.bit.cangjingge.common.service.FictionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;

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

    @GetMapping("/oauth/user")
    public Principal user(Principal principal) {
        return principal;
    }

}
