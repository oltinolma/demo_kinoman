package uz.oltinolma.producer.security.mvc.permission.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;
import uz.oltinolma.producer.security.mvc.permission.Permission;
import uz.oltinolma.producer.security.mvc.permission.dao.PermissionDao;

@Service
public class PermissionServicePostgresImpl extends PermissionService {
    private PermissionDao permissionDao;
    private PermissionService permissionServiceH2Impl;

    @Autowired
    public void setPermissionServiceH2Impl(PermissionService permissionServiceH2Impl) {
        this.permissionServiceH2Impl = permissionServiceH2Impl;
    }

    public PermissionDao getPermissionDao() {
        return permissionDao;
    }

    @Autowired
    public void setPermissionDao(PermissionDao permissionDaoPostgresImpl) {
        this.permissionDao = permissionDaoPostgresImpl;
    }

    public void insert(Permission permissions) {
        int id = permissionDao.insert(permissions);
        permissions.setId(id);
        permissionServiceH2Impl.insert(permissions);
    }

    public BaseResponse update(Permission permissions) {
        permissionServiceH2Impl.update(permissions);
        return permissionDao.update(permissions);
    }

    public BaseResponse delete(int id) {
        permissionServiceH2Impl.delete(id);
        return permissionDao.delete(id);
    }
}
