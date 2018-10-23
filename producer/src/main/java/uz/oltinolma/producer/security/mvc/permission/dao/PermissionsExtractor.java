package uz.oltinolma.producer.security.mvc.permission.dao;

import org.springframework.stereotype.Component;
import uz.oltinolma.producer.security.mvc.permission.Permission;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PermissionsExtractor {
    public Permission extract(ResultSet rs) throws SQLException {
        Permission permissions = new Permission();
        permissions.setId(rs.getInt("id"));
        permissions.setName(rs.getString("name"));
        permissions.setInfo(rs.getString("notes"));
        return permissions;
    }
}
