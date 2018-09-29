package uz.oltinolma.producer.security.mvc.rolepermissions.service;

import org.springframework.transaction.annotation.Transactional;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;
import uz.oltinolma.producer.security.mvc.role.Role;
import uz.oltinolma.producer.security.mvc.rolepermissions.RolesPermissions;
import uz.oltinolma.producer.security.mvc.rolepermissions.dao.RolesPermissionsDao;

import java.util.List;

public abstract class RolesPermissionsService {
    public abstract RolesPermissionsDao getDao();

    public abstract void setDao(RolesPermissionsDao dao);

    public List<RolesPermissions> list() {
        return getDao().list();
    }

    public List<Role> countList() {
        return getDao().countList();
    }

    public RolesPermissions get(int id) {
        return getDao().get(id);
    }

    public abstract BaseResponse insert(RolesPermissions rolesPermissions);

    @Transactional
    public BaseResponse insertAll(List<RolesPermissions> rolesPermissions) {
        return getDao().insertAll(rolesPermissions);
    }

    public abstract BaseResponse update(RolesPermissions rolesPermissions);

    public abstract BaseResponse delete(int id);
}
