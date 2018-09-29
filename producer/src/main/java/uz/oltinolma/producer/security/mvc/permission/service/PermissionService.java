package uz.oltinolma.producer.security.mvc.permission.service;

import org.springframework.transaction.annotation.Transactional;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;
import uz.oltinolma.producer.security.mvc.permission.Permissions;
import uz.oltinolma.producer.security.mvc.permission.dao.PermissionDao;

import java.util.List;
import java.util.Set;

public abstract class PermissionService {
    public abstract PermissionDao getPermissionDao();
    public abstract void setPermissionDao(PermissionDao permissionDao);
    public Set<String> getByLogin(String login) {
        return getPermissionDao().getByLogin(login);
    }

    public List<Permissions> list() {
        return getPermissionDao().list();
    }

    public Permissions get(int id) {
        return getPermissionDao().get(id);
    }

    public abstract BaseResponse insert(Permissions permissions);

    @Transactional
    public BaseResponse insertAll(List<Permissions> roles) {
        return getPermissionDao().insertAll(roles);
    }

    public abstract BaseResponse update(Permissions permissions);

    public abstract BaseResponse delete(int id);
}
