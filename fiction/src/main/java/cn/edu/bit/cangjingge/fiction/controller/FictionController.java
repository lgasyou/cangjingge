package cn.edu.bit.cangjingge.fiction.controller;

import cn.edu.bit.cangjingge.common.exception.BusinessException;
import cn.edu.bit.cangjingge.common.response.Response;
import cn.edu.bit.cangjingge.common.response.ResponseUtil;
import cn.edu.bit.cangjingge.fiction.service.FictionServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class FictionController {

    @Resource
    private FictionServiceImpl fictionService;

    @GetMapping("/hello-error")
    public Response<String> helloError() {
        throw new BusinessException(400, "?");
    }

    @GetMapping("/hello")
    @PreAuthorize("hasRole('ADMIN')")
    public Response<Integer> hello() {
        return ResponseUtil.success(fictionService.hello());
    }

}
