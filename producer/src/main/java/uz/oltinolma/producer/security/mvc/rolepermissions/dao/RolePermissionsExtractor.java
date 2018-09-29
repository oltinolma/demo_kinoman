package uz.oltinolma.producer.security.mvc.rolepermissions.dao;

import org.springframework.stereotype.Component;
import uz.oltinolma.producer.security.mvc.rolepermissions.RolesPermissions;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RolePermissionsExtractor {
    public RolesPermissions extract(ResultSet rs) throws SQLException {
        RolesPermissions rp = new RolesPermissions();
        rp.setId(rs.getInt("id"));
        rp.setId_permission(rs.getInt("id_permission"));
        rp.setId_role(rs.getInt("id_role"));
        rp.setRole_name(rs.getString("role_name"));
        rp.setPermission_name(rs.getString("permission_name"));
        return rp;
    }
}
