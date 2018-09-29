package uz.oltinolma.producer.security.mvc.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.oltinolma.producer.security.mvc.user.User;
import uz.oltinolma.producer.security.mvc.user.dao.UserDao;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;

@Service
public class UserServiceH2Impl extends UserService {

    private UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }
    @Autowired
    public void setUserDao(UserDao userDaoH2Impl) {
        this.userDao = userDaoH2Impl;
    }

    public BaseResponse delete(int id) {
        return userDao.delete(id);
    }

    public BaseResponse insert(User employee) {
        return userDao.insert(employee);
    }

    public BaseResponse update(User employee) {
        return userDao.update(employee);
    }

}
