package cn.edu.bit.cangjingge.authorization.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@ApiModel("授权信息")
@Data
public class UserAuthInfo {

    public UserAuthInfo(String token, Date expiresAt) {
        this.token = token;
        this.expiresAt = expiresAt;
    }

    private String token;

    private Date expiresAt;

}
