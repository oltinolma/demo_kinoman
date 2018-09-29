package uz.oltinolma.producer.security.mvc.role.service;

import org.springframework.transaction.annotation.Transactional;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;
import uz.oltinolma.producer.security.mvc.role.Role;
import uz.oltinolma.producer.security.mvc.role.dao.RoleDao;

import java.util.List;

public abstract class RoleService {
    public abstract void setRoleDao(RoleDao roleDao);

    public abstract RoleDao getRoleDao();

    public List<Role> list() {
        return getRoleDao().getAllRoles();
    }

    public Role get(int id) {
        return getRoleDao().getRoleById(id);
    }

    public abstract BaseResponse insert(Role role);
    @Transactional
    public BaseResponse insertAll(List<Role> roles) {
        return getRoleDao().insertAll(roles);
    }

    public abstract BaseResponse update(Role role);

    public abstract BaseResponse delete(int id);
}
