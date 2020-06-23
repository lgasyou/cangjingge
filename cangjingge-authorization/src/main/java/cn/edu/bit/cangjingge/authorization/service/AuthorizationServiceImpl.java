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
            final String phoneNumber,
            final String password
    ) {
        UserAuth userAuth = new UserAuth();
        userAuth.setPhoneNumber(phoneNumber);
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
        UserAuth userAuth = userAuthDao.findUserAuthByPhoneNumber(phoneNumber);
        if (userAuth == null) {
            throw new BusinessException(ResponseStatusEnum.TOKEN_INVALID);
        }
        String secret = userAuth.getPassword();

        boolean valid = TokenUtil.verify(token, phoneNumber, secret);
        boolean expired = userAuth.getExpirationTime().before(new Date());
        if (!valid || expired) {
            throw new BusinessException(ResponseStatusEnum.TOKEN_INVALID);
        }
    }

    private UserAuthInfo refreshToken(
            UserAuth userAuth
    ) {
        String phoneNumber = userAuth.getPhoneNumber();
        String secret = userAuth.getPassword();
        Date expiresAt = new Date(System.currentTimeMillis() + 1000 * 15*24*60*60);
        String token = TokenUtil.sign(phoneNumber, secret, expiresAt);

        userAuth.setExpirationTime(expiresAt);
        userAuthDao.save(userAuth);

        return new UserAuthInfo(token, expiresAt);
    }

    public UserAuthInfo refreshToken() throws BusinessException {
        String phoneNumber = TokenUtil.getTokenFromHeaders();
        return refreshToken(phoneNumber);
    }

    private UserAuthInfo refreshToken(
            String phoneNumber
    ) throws BusinessException {
        UserAuth userAuth = userAuthDao.findUserAuthByPhoneNumber(phoneNumber);
        if (userAuth == null) {
            throw new BusinessException(ResponseStatusEnum.USER_NOT_FOUND);
        }
        return refreshToken(userAuth);
    }

    public UserAuthInfo loginWithPassword(
            final String phoneNumber,
            final String password
    ) throws BusinessException {
        UserAuth userAuth = userAuthDao.findUserAuthByPhoneNumberAndPassword(phoneNumber, password);
        if (userAuth == null) {
            throw new BusinessException(ResponseStatusEnum.INCORRECT_USERNAME_OR_PASSWORD);
        }
        return refreshToken(userAuth);
    }

    public void resetPassword(
            final String phoneNumber,
            final String newPassword
    ) throws BusinessException {
        UserAuth userAuth = userAuthDao.findUserAuthByPhoneNumber(phoneNumber);
        if (userAuth == null) {
            throw new BusinessException(ResponseStatusEnum.USER_NOT_FOUND);
        }
        userAuth.setPassword(newPassword);
        userAuthDao.save(userAuth);
    }

}
