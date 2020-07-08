package cn.edu.bit.cangjingge.common.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("图片服务的授权信息")
@Data
public class AliyunOssAuthorization {

    public AliyunOssAuthorization(String accessKeyId, String accessKeySecret, String securityToken) {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.securityToken = securityToken;
    }

    private String accessKeyId;

    private String accessKeySecret;

    private String securityToken;

}
