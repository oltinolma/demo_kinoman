package uz.oltinolma.producer.security.mvc.user.dao;

import uz.oltinolma.producer.security.mvc.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserExtractor {
    public static User extract(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId((UUID) rs.getObject("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        user.setLogin(rs.getString("login"));
        user.setEnable(rs.getBoolean("enable"));
        user.setRole(rs.getString("role_name"));
        user.setId_roles(rs.getLong("id_role"));
        return user;
    }
}
