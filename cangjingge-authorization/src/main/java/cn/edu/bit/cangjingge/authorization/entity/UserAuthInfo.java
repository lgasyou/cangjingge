package cn.edu.bit.cangjingge.authorization.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@ApiModel("授权信息")
@Data
@AllArgsConstructor
public class UserAuthInfo {

    @ApiModelProperty("访问令牌")
    private String accessToken;

    @ApiModelProperty("令牌过期时间")
    private Date expiresAt;

    @ApiModelProperty("角色")
    private String[] roles;

}
