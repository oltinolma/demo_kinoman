package uz.oltinolma.producer.security.mvc.role.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;
import uz.oltinolma.producer.security.mvc.role.Role;
import uz.oltinolma.producer.security.mvc.role.dao.RoleDao;


@Service("roleServiceH2Impl")
public class RoleServiceH2Impl extends RoleService {
    private RoleDao roleDaoH2Impl;

    @Autowired
    public void setRoleDao(RoleDao roleDaoH2Impl) {
        this.roleDaoH2Impl = roleDaoH2Impl;
    }

    public RoleDao getRoleDao() {
        return roleDaoH2Impl;
    }

    public BaseResponse insert(Role role) {
        return roleDaoH2Impl.insert(role);
    }

    public BaseResponse update(Role role) {
        return roleDaoH2Impl.update(role);
    }

    public BaseResponse delete(int id) {
        return roleDaoH2Impl.delete(id);
    }

}
