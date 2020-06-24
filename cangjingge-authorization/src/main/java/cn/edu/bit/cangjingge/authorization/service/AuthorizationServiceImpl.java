package cn.edu.bit.cangjingge.authorization.service;

import cn.edu.bit.cangjingge.authorization.dao.UserAuthDao;
import cn.edu.bit.cangjingge.authorization.entity.UserAuth;
import cn.edu.bit.cangjingge.authorization.entity.UserAuthInfo;
import cn.edu.bit.cangjingge.common.exception.BusinessException;
import cn.edu.bit.cangjingge.common.response.ResponseStatusEnum;
import cn.edu.bit.cangjingge.common.util.TokenUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class AuthorizationServiceImpl {

    @Resource
    private UserAuthDao userAuthDao;

    public void createUserAuth(
            final String username,
            final String password
    ) {
        UserAuth userAuth = new UserAuth();
        userAuth.setUsername(username);
        userAuth.setPassword(password);
        userAuthDao.save(userAuth);
    }

    public void checkToken(
            final String token
    ) throws BusinessException {
        if (token == null) {
            throw new BusinessException(ResponseStatusEnum.TOKEN_INVALID);
        }
        String phoneNumber = TokenUtil.getUsername(token);
        if (phoneNumber == null) {
            throw new BusinessException(ResponseStatusEnum.TOKEN_INVALID);
        }
        UserAuth userAuth = userAuthDao.findUserAuthByUsername(phoneNumber);
        if (userAuth == null) {
            throw new BusinessException(ResponseStatusEnum.TOKEN_INVALID);
        }
        String roles = userAuth.getRoles();
        String secret = userAuth.getPassword();

        boolean valid = TokenUtil.verify(token, phoneNumber, roles, secret);
        boolean expired = userAuth.getExpirationTime().before(new Date());
        if (!valid || expired) {
            throw new BusinessException(ResponseStatusEnum.TOKEN_INVALID);
        }
    }

    private UserAuthInfo refreshTokenWithUserAuth(
            UserAuth userAuth
    ) {
        String username = userAuth.getUsername();
        String secret = userAuth.getPassword();
        String roles = userAuth.getRoles();
        Date expiresAt = new Date(System.currentTimeMillis() + 1000 * 15*24*60*60);
        String token = TokenUtil.sign(username, roles, secret, expiresAt);

        userAuth.setExpirationTime(expiresAt);
        userAuthDao.save(userAuth);

        String[] roleList = roles.split(",");
        return new UserAuthInfo(token, expiresAt, roleList);
    }

    public UserAuthInfo refreshToken() throws BusinessException {
        String phoneNumber = TokenUtil.getUsernameFromHeaders();
        return refreshTokenWithUsername(phoneNumber);
    }

    private UserAuthInfo refreshTokenWithUsername(
            String phoneNumber
    ) throws BusinessException {
        UserAuth userAuth = userAuthDao.findUserAuthByUsername(phoneNumber);
        if (userAuth == null) {
            throw new BusinessException(ResponseStatusEnum.USER_NOT_FOUND);
        }
        return refreshTokenWithUserAuth(userAuth);
    }

    public UserAuthInfo loginWithPassword(
            final String phoneNumber,
            final String password
    ) throws BusinessException {
        UserAuth userAuth = userAuthDao.findUserAuthByUsernameAndPassword(phoneNumber, password);
        if (userAuth == null) {
            throw new BusinessException(ResponseStatusEnum.INCORRECT_USERNAME_OR_PASSWORD);
        }
        return refreshTokenWithUserAuth(userAuth);
    }

    public void resetPassword(
            final String newPassword
    ) throws BusinessException {
        String username = TokenUtil.getUsernameFromHeaders();
        UserAuth userAuth = userAuthDao.findUserAuthByUsername(username);
        if (userAuth == null) {
            throw new BusinessException(ResponseStatusEnum.USER_NOT_FOUND);
        }
        userAuth.setPassword(newPassword);
        userAuthDao.save(userAuth);
    }

}
