package cn.edu.bit.cangjingge.authorization.dao;

import cn.edu.bit.cangjingge.authorization.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthDao extends JpaRepository<UserAuth, Long> {

    UserAuth findUserAuthByUsername(String username);

    UserAuth findUserAuthByUsernameAndPassword(String username, String password);

}
