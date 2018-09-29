package uz.oltinolma.producer.security.mvc.rolepermissions.dao;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.oltinolma.producer.security.common.LogUtil;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;
import uz.oltinolma.producer.security.mvc.rolepermissions.RolesPermissions;

import java.util.HashMap;
import java.util.Map;

@Repository
public class RolesPermissionsDaoPostgresImpl extends RolesPermissionsDao {
    private static final Logger logger = LogUtil.getInstance();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(javax.sql.DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public NamedParameterJdbcTemplate getTemplate() {
        return namedParameterJdbcTemplate;
    }

    @Override
    public BaseResponse insert(RolesPermissions rolesPermissions) {
        Map<String, Object> map = new HashMap<>();
        map.put("id_permission", rolesPermissions.getId_permission());
        map.put("id_role", rolesPermissions.getId_role());
        String sql = "INSERT INTO roles_permissions(id_permission,id_role) VALUES (:id_permission,:id_role)";
        try {
            this.namedParameterJdbcTemplate.update(sql, map);
        } catch (DuplicateKeyException d) {
            logger.error("Couldn't insert rolesPermissions into postgresql.", d);
            return baseResponses.duplicateKeyErrorResponse("");
        } catch (Exception e) {
            logger.error("Couldn't insert rolesPermissions into postgresql.", e);
            return baseResponses.serverErrorResponse();
        }
        return baseResponses.successMessage();
    }

}
