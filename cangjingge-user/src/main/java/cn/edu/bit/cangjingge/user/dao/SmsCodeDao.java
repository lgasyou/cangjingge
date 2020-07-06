package cn.edu.bit.cangjingge.user.dao;

import cn.edu.bit.cangjingge.user.entity.SmsCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsCodeDao extends JpaRepository<SmsCode, Long> {

    SmsCode findSmsCodeByPhoneNumber(String phoneNumber);

    SmsCode findSmsCodeByPhoneNumberAndSmsCode(String phoneNumber, String smsCode);

}
