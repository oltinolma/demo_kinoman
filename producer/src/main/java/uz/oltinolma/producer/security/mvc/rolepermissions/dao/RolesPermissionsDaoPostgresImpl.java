package uz.oltinolma.producer.security.mvc.rolepermissions.dao;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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
    private SimpleJdbcInsert rpInsert;

    @Autowired
    public void setDataSource(javax.sql.DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        rpInsert = new SimpleJdbcInsert(dataSource).withTableName("role_permission").usingGeneratedKeyColumns("id");
    }

    public NamedParameterJdbcTemplate getTemplate() {
        return namedParameterJdbcTemplate;
    }

    @Override
    public int insert(RolesPermissions rolesPermissions) {
        Map<String, Object> params = new HashMap<>();
        params.put("id_permission", rolesPermissions.getId_permission());
        params.put("id_role", rolesPermissions.getId_role());
        return rpInsert.executeAndReturnKey(params).intValue();
    }

}
