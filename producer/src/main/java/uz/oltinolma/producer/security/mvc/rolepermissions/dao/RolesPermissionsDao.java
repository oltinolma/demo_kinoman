package uz.oltinolma.producer.security.mvc.rolepermissions.dao;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import uz.oltinolma.producer.security.common.BaseResponses;
import uz.oltinolma.producer.security.common.LogUtil;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;
import uz.oltinolma.producer.security.mvc.role.Role;
import uz.oltinolma.producer.security.mvc.rolepermissions.RolesPermissions;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class RolesPermissionsDao {
    private static final Logger logger = LogUtil.getInstance();
    @Autowired
    private RolePermissionsExtractor rolePermissionsExtractor;
    @Autowired
    protected BaseResponses baseResponses;
    @Autowired
    public abstract void setDataSource(DataSource dataSource);
    public abstract NamedParameterJdbcTemplate getTemplate();
    public List<RolesPermissions> list() {
        String sql = "SELECT * FROM view_role_permission";
        return getTemplate().query(sql, (resultSet, i) -> rolePermissionsExtractor.extract(resultSet));
    }

    public List<Role> countList() {
        String sql = "SELECT r.id, r.name, (SELECT COUNT(roles_id) FROM role_permission WHERE roles_id = r.id) AS count_permissions FROM roles r";
        return getTemplate().query(sql, (resultSet, i) -> {
            Role role = new Role();
            role.setId(resultSet.getLong("id"));
            role.setName(resultSet.getString("name"));
            return role;
        });
    }

    public RolesPermissions get(int id) {
        String sql = "SELECT * FROM view_role_permission WHERE id=:id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        return this.getTemplate().query(sql, parameterSource, resultSet -> {
            if (resultSet.next()) {
                return rolePermissionsExtractor.extract(resultSet);
            }
            return null;
        });
    }

    /**Override in order to use */
    public BaseResponse insertAll(List<RolesPermissions> rolesPermissions) {
        throw new UnsupportedOperationException();
    }
    /**Override in order to use */
    public BaseResponse insert(RolesPermissions rolesPermissions) {
        throw new UnsupportedOperationException();
    }


    public BaseResponse update(RolesPermissions rolesPermissions) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", rolesPermissions.getId());
        map.put("id_permission", rolesPermissions.getId_permission());
        map.put("id_role", rolesPermissions.getId_role());
        String sql = "UPDATE role_permission SET id_permission=:id_permission,id_role=:id_role WHERE id=:id";
        try {
            getTemplate().update(sql, map);
        } catch (DuplicateKeyException d) {
            logger.error("Couldn't update the rolesPermissions.", d);
            return baseResponses.duplicateKeyErrorResponse("");
        } catch (Exception e) {
            logger.error("Couldn't update the rolesPermissions.", e);
            return baseResponses.serverErrorResponse();
        }
        return baseResponses.successMessage();
    }

    public BaseResponse delete(int id) {
        String sql = "DELETE FROM role_permission WHERE id=:id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        try {
            getTemplate().update(sql, parameterSource);
        } catch (Exception e) {
            logger.error("Couldn't delete the rolesPermissions.", e);
            return baseResponses.serverErrorResponse();
        }
        return baseResponses.successMessage();
    }
}
