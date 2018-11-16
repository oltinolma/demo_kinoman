package uz.oltinolma.producer.security.mvc.rolepermissions.dao;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.oltinolma.producer.common.LogUtil;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;
import uz.oltinolma.producer.security.mvc.rolepermissions.RolesPermissions;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RolesPermissionsDaoH2Impl extends RolesPermissionsDao {
    private static final Logger logger = LogUtil.getInstance();
    @Autowired
    private NamedParameterJdbcTemplate template;

    @Autowired
    @Qualifier("h2Datasource")
    public void setDataSource(DataSource h2Datasource) {
        template = new NamedParameterJdbcTemplate(h2Datasource);
    }

    public NamedParameterJdbcTemplate getTemplate() {
        return template;
    }

    @Override
    public BaseResponse insertAll(List<RolesPermissions> rolesPermissions) {
        String sql = "INSERT INTO role_permission(id, id_role, id_permission) VALUES(:id, :id_role, :id_permission)";
        Map<String, Object>[] batch = new HashMap[rolesPermissions.size()];
        for (int i = 0; i < rolesPermissions.size(); i++) {
            RolesPermissions rp = rolesPermissions.get(i);
            Map<String, Object> map = new HashMap<>();
            map.put("id", rp.getId());
            map.put("id_role", rp.getId_role());
            map.put("id_permission", rp.getId_permission());
            batch[i] = map;
        }
        try {
            template.batchUpdate(sql, batch);
        } catch (Exception e) {
            logger.error("Couldn't insert rolesPermissions into h2.", e);
            return baseResponses.serverErrorResponse();
        }
        return baseResponses.successMessage();
    }

    public int insert(RolesPermissions rolesPermissions) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", rolesPermissions.getId());
        map.put("id_permission", rolesPermissions.getId_permission());
        map.put("id_role", rolesPermissions.getId_role());
        String sql = "INSERT INTO role_permission(id, id_permission,id_role) VALUES (:id, :id_permission,:id_role)";

        return this.template.update(sql, map);
    }
}
