package cn.edu.bit.cangjingge.user.service;

import cn.edu.bit.cangjingge.common.exception.BusinessException;
import cn.edu.bit.cangjingge.common.response.Response;
import cn.edu.bit.cangjingge.common.response.ResponseStatusEnum;
import cn.edu.bit.cangjingge.common.response.ResponseUnpacker;
import cn.edu.bit.cangjingge.common.service.AuthorizationService;
import cn.edu.bit.cangjingge.user.dao.UserDao;
import cn.edu.bit.cangjingge.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserServiceImpl {

    @Resource
    private UserDao userDao;

    @Resource
    private AuthorizationService authService;

    @Transactional
    public User register(
            final String username,
            final String password
    ) throws BusinessException {
        if (userDao.findByUsername(username).isPresent()) {
            throw new BusinessException(ResponseStatusEnum.USER_ALREADY_EXISTED);
        }

        Response<String> response = authService.register(username, password);
        ResponseUnpacker.unpack(response);

        User user = new User();
        user.setUsername(username);
        userDao.save(user);
        return user;
    }

    public User getById(
            final Long id
    ) {
        return userDao.findById(id).orElseThrow(() -> new BusinessException(ResponseStatusEnum.USER_NOT_FOUND));
    }

}
