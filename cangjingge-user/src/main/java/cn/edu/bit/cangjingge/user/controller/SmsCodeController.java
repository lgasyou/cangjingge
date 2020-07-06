package cn.edu.bit.cangjingge.user.controller;

import cn.edu.bit.cangjingge.common.response.Response;
import cn.edu.bit.cangjingge.common.response.ResponseUtil;
import cn.edu.bit.cangjingge.user.service.SmsCodeManager;
import com.aliyuncs.exceptions.ClientException;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SmsCodeController {

    @Resource
    private SmsCodeManager smsCodeManager;

    @ApiOperation("使用用户ID获取用户基本信息")
    @PostMapping("/sms-code")
    public Response<String> getById(
            @RequestParam("phoneNumber") final String phoneNumber
    ) throws ClientException {
        smsCodeManager.sendRandomCode(phoneNumber);
        return ResponseUtil.success();
    }

}
