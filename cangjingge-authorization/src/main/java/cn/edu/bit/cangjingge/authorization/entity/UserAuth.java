package cn.edu.bit.cangjingge.authorization.entity;

import cn.edu.bit.cangjingge.common.security.UserRoleEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class UserAuth {

    // 数据库编号
    @Id
    @GeneratedValue
    private Long id;

    // 用户名
    private String username = "";

    // 令牌过期时间
    private Date expirationTime = new Date();

    // 用户密码
    private String password = "";

    // 用户角色，使用逗号分割
    private String roles = UserRoleEnum.ROLE_USER.name();

}
