package uz.oltinolma.producer.security.config;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import uz.oltinolma.producer.security.common.ExcludeFromTests;
import uz.oltinolma.producer.security.common.LogUtil;
import uz.oltinolma.producer.security.mvc.user.service.UserService;
import uz.oltinolma.producer.security.mvc.permission.service.PermissionService;
import uz.oltinolma.producer.security.mvc.role.service.RoleService;
import uz.oltinolma.producer.security.mvc.rolepermissions.service.RolesPermissionsService;

import javax.annotation.PostConstruct;

@Component
@ExcludeFromTests
public class InitAll {
    private static final Logger logger = LogUtil.getInstance();
    @Autowired
    private PermissionService permissionServicePostgresImpl;
    @Autowired
    private PermissionService permissionServiceH2Impl;
    @Autowired
    private RoleService roleServicePostgresImpl;
    @Autowired
    private RoleService roleServiceH2Impl;
    @Autowired
    private RolesPermissionsService rolesPermissionsServicePostgresImpl;
    @Autowired
    private RolesPermissionsService rolesPermissionsServiceH2Impl;
    @Autowired
    private UserService userServicePostgresImpl;
    @Autowired
    private UserService userServiceH2Impl;

    @PostConstruct
    public void initH2() {
        permissionServiceH2Impl.insertAll(permissionServicePostgresImpl.list());
        roleServiceH2Impl.insertAll(roleServicePostgresImpl.list());
        rolesPermissionsServiceH2Impl.insertAll(rolesPermissionsServicePostgresImpl.list());
        userServiceH2Impl.insertAll(userServicePostgresImpl.list());
        logger.info("H2 initialization completed successfully");
    }
}
