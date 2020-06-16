package cn.edu.bit.cangjingge.authorization.controller;

import cn.edu.bit.cangjingge.common.response.ResultUnpacker;
import cn.edu.bit.cangjingge.common.service.FictionService;
import cn.edu.bit.cangjingge.common.response.Result;
import cn.edu.bit.cangjingge.common.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizationController {

    @Autowired
    private FictionService fictionService;

    @GetMapping("/hello")
    public Result<Integer> hello() {
        Result<Integer> result = fictionService.hello();
        Integer hello = ResultUnpacker.unpack(result);
        return ResultUtil.success(hello);
    }

}
