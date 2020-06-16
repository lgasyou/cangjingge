package cn.edu.bit.cangjingge.common.service;

import cn.edu.bit.cangjingge.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "FICTION", fallbackFactory = FictionServiceFallbackFactory.class)
public interface FictionService {

    @GetMapping("/fictions/hello")
    Result<Integer> hello();

    @GetMapping("/fictions/hello-error")
    Result<String> helloError();

}
