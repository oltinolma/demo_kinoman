package uz.oltinolma.producer.security.mvc.permission.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;
import uz.oltinolma.producer.security.mvc.permission.Permission;
import uz.oltinolma.producer.security.mvc.permission.dao.PermissionDao;

@Service
public class PermissionServiceH2Impl extends PermissionService {
    private PermissionDao permissionDao;

    public PermissionDao getPermissionDao() {
        return permissionDao;
    }

    @Autowired
    public void setPermissionDao(PermissionDao permissionDaoH2Impl) {
        this.permissionDao = permissionDaoH2Impl;
    }

    public void insert(Permission permissions) {
        permissionDao.insert(permissions);
    }

    public void update(Permission permissions) {
         permissionDao.update(permissions);
    }

    public void delete(int id) {
        permissionDao.delete(id);
    }

}
