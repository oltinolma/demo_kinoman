package uz.oltinolma.producer.security.mvc.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.oltinolma.producer.security.mvc.user.User;
import uz.oltinolma.producer.security.mvc.user.dao.UserDao;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;

@Service
public class UserServicePostgresImpl extends UserService {
    private UserDao userDao;
    @Autowired
    private UserService userServiceH2Impl;
    @Autowired
    public void setUserDao(UserDao userDaoPostgresImpl) {
        this.userDao = userDaoPostgresImpl;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public BaseResponse delete(int id) {
        userServiceH2Impl.delete(id);
        return userDao.delete(id);
    }

    public BaseResponse insert(User employee) {
        userServiceH2Impl.insert(employee);
        return userDao.insert(employee);
    }

    public BaseResponse update(User employee) {
        userServiceH2Impl.update(employee);
        return userDao.update(employee);
    }
}
