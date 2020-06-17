package cn.edu.bit.cangjingge.common.service;

import cn.edu.bit.cangjingge.common.response.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "FICTION")
public interface FictionService {

    @GetMapping("/hello")
    Response<Integer> hello();

    @GetMapping("/hello-error")
    Response<String> helloError();

}
