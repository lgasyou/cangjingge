package cn.edu.bit.cangjingge.fiction.controller;

import cn.edu.bit.cangjingge.common.exception.BusinessException;
import cn.edu.bit.cangjingge.common.response.Result;
import cn.edu.bit.cangjingge.common.response.ResultUtil;
import cn.edu.bit.cangjingge.fiction.service.FictionServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class FictionController {

    @Resource
    private FictionServiceImpl fictionService;

    @GetMapping("/hello-error")
    public Result<String> helloError() {
        throw new BusinessException(400, "?");
    }

    @GetMapping("/hello")
    public Result<Integer> hello() {
        return ResultUtil.success(fictionService.hello());
    }

}
