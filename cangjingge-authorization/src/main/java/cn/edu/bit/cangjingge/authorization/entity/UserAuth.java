package cn.edu.bit.cangjingge.authorization.entity;

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

    // 手机号
    private String phoneNumber = "";

    // 令牌过期时间
    private Date expirationTime = new Date();

    // 用户密码
    private String password = "";

}
