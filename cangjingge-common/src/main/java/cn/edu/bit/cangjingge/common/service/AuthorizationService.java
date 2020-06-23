package cn.edu.bit.cangjingge.common.service;

import cn.edu.bit.cangjingge.common.response.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "AUTHORIZATION")
public interface AuthorizationService {

    @GetMapping("/token/validation")
    Response<String> checkToken(@RequestParam("token") final String token);

}
