package cn.edu.bit.cangjingge.common.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("用户鉴权信息")
@Data
public class UserAuth {

    private Long id;

    private String accessToken;

    private String refreshToken;

}
