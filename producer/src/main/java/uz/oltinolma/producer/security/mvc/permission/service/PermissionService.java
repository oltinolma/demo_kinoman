package uz.oltinolma.producer.security.mvc.permission.service;

import org.springframework.transaction.annotation.Transactional;
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
    public void insertAll(List<Permission> roles) {
        getPermissionDao().insertAll(roles);
    }

    public abstract void update(Permission permissions);

    public abstract void delete(int id);

    public void deleteAll() {
        getPermissionDao().deleteAll();
    }
}
