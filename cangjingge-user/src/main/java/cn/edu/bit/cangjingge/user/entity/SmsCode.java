package cn.edu.bit.cangjingge.user.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class SmsCode {

    // 数据库编号
    @Id
    @GeneratedValue
    private Long id;

    // 手机号
    private String phoneNumber = "";

    // 验证码
    private String smsCode = "";

    // 最早可再次发送时间
    private Date nextRetryTime;

    // 过期时间
    private Date expirationTime;

}
