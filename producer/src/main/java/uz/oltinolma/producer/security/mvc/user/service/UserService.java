package uz.oltinolma.producer.security.mvc.user.service;

import org.springframework.transaction.annotation.Transactional;
import uz.oltinolma.producer.security.mvc.user.User;
import uz.oltinolma.producer.security.mvc.user.dao.UserDao;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;

import java.util.List;
import java.util.UUID;

public abstract class UserService {
    public abstract UserDao getUserDao();
    public User findByLogin(String login) {
        return getUserDao().findByLogin(login);
    }

    public List<User> list() {
        return getUserDao().list();
    }

    public User get(UUID id) {
        return getUserDao().get(id);
    }

    public abstract BaseResponse delete(int id);

    public abstract BaseResponse insert(User employee);

    @Transactional
    public BaseResponse insertAll(List<User> employees) {
        return getUserDao().insertAll(employees);
    }

    public abstract BaseResponse update(User employee);

    public List<String> getEmployeeInfoByLogin(String login) {
        return getUserDao().getEmployeeInfoByLogin(login);
    }
}
