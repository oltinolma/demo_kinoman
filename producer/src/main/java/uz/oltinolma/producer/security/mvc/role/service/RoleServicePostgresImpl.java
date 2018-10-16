package uz.oltinolma.producer.security.mvc.role.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;
import uz.oltinolma.producer.security.mvc.role.Role;
import uz.oltinolma.producer.security.mvc.role.dao.RoleDao;

@Service("roleServicePostgresImpl")
public class RoleServicePostgresImpl extends RoleService {
    private RoleDao roleDaoPostgresImpl;
    @Autowired
    private RoleService roleServiceH2Impl;

    @Autowired
    public void setRoleDao(RoleDao roleDaoPostgresImpl) {
        this.roleDaoPostgresImpl = roleDaoPostgresImpl;
    }

    public RoleDao getRoleDao() {
        return roleDaoPostgresImpl;
    }

    @Transactional
    public int insert(Role role) {
        int id = roleDaoPostgresImpl.insert(role);
        role.setId((long) id);
        return roleServiceH2Impl.insert(role);
    }

    public BaseResponse update(Role role) {
        roleServiceH2Impl.update(role);
        return roleDaoPostgresImpl.update(role);
    }

    public BaseResponse delete(int id) {
        roleServiceH2Impl.delete(id);
        return roleDaoPostgresImpl.delete(id);
    }
}
