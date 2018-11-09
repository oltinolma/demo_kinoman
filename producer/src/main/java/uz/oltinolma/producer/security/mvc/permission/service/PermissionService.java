package uz.oltinolma.producer.security.mvc.permission.service;

import org.springframework.transaction.annotation.Transactional;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;
import uz.oltinolma.producer.security.mvc.permission.Permission;
import uz.oltinolma.producer.security.mvc.permission.dao.PermissionDao;

import java.util.List;
import java.util.Set;

public abstract class PermissionService {
    public abstract PermissionDao getPermissionDao();
    public abstract void setPermissionDao(PermissionDao permissionDao);
    public Set<String> getPermissionsForUser(String login) {
        return getPermissionDao().getByLogin(login);
    }

    public List<Permission> list() {
        return getPermissionDao().list();
    }

    public Permission get(int id) {
        return getPermissionDao().get(id);
    }

    public abstract void insert(Permission permissions);

    @Transactional
    public BaseResponse insertAll(List<Permission> roles) {
        return getPermissionDao().insertAll(roles);
    }

    public abstract BaseResponse update(Permission permissions);

    public abstract BaseResponse delete(int id);
}
