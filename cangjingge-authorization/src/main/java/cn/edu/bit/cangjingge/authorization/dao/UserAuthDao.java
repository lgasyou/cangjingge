package cn.edu.bit.cangjingge.authorization.dao;

import cn.edu.bit.cangjingge.authorization.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthDao extends JpaRepository<UserAuth, Long> {

    UserAuth findUserAuthByPhoneNumber(String phoneNumber);

    UserAuth findUserAuthByPhoneNumberAndPassword(String phoneNumber, String password);

}
