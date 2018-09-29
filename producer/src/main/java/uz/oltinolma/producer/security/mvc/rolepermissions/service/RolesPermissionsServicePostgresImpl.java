package uz.oltinolma.producer.security.mvc.rolepermissions.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;
import uz.oltinolma.producer.security.mvc.rolepermissions.RolesPermissions;
import uz.oltinolma.producer.security.mvc.rolepermissions.dao.RolesPermissionsDao;

@Service
public class RolesPermissionsServicePostgresImpl extends RolesPermissionsService {
    private RolesPermissionsDao dao;
    @Autowired
    private RolesPermissionsService rolesPermissionsServiceH2Impl;
    public RolesPermissionsDao getDao() {
        return dao;
    }

    @Autowired
    public void setDao(RolesPermissionsDao rolesPermissionsDaoPostgresImpl) {
        this.dao = rolesPermissionsDaoPostgresImpl;
    }

    public BaseResponse insert(RolesPermissions rolesPermissions) {
        rolesPermissionsServiceH2Impl.insert(rolesPermissions);
        return dao.insert(rolesPermissions);
    }

    public BaseResponse update(RolesPermissions rolesPermissions) {
        rolesPermissionsServiceH2Impl.update(rolesPermissions);
        return dao.update(rolesPermissions);
    }

    public BaseResponse delete(int id) {
        rolesPermissionsServiceH2Impl.delete(id);
        return dao.delete(id);
    }
}
