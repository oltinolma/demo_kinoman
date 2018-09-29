package uz.oltinolma.producer.security.mvc.rolepermissions.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;
import uz.oltinolma.producer.security.mvc.rolepermissions.RolesPermissions;
import uz.oltinolma.producer.security.mvc.rolepermissions.dao.RolesPermissionsDao;

@Service
public class RolesPermissionsServiceH2Impl extends RolesPermissionsService {
    private RolesPermissionsDao dao;

    public RolesPermissionsDao getDao() {
        return dao;
    }

    @Autowired
    public void setDao(RolesPermissionsDao rolesPermissionsDaoH2Impl) {
        this.dao = rolesPermissionsDaoH2Impl;
    }

    public BaseResponse insert(RolesPermissions rolesPermissions) {
        return dao.insert(rolesPermissions);
    }

    public BaseResponse update(RolesPermissions rolesPermissions) {
        return dao.update(rolesPermissions);
    }

    public BaseResponse delete(int id) {
        return dao.delete(id);
    }
}
