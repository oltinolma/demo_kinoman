package uz.oltinolma.producer.security.mvc.role.dao;

import org.springframework.stereotype.Component;
import uz.oltinolma.producer.security.mvc.role.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RoleExtractor {
    public Role extract(ResultSet rs) throws SQLException {
        Role role = new Role();
        role.setId(rs.getLong("id"));
        role.setName(rs.getString("name"));
        role.setId_status(rs.getInt("id_status"));
        return role;
    }
}
