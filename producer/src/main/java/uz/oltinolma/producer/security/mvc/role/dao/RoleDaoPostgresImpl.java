package uz.oltinolma.producer.security.mvc.role.dao;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.oltinolma.producer.security.common.LogUtil;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;
import uz.oltinolma.producer.security.mvc.role.Role;

import java.util.HashMap;
import java.util.Map;

@Repository("roleDaoPostgresImpl")
public class RoleDaoPostgresImpl extends RoleDao {
    private static final Logger logger = LogUtil.getInstance();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    @Qualifier("datasource")
    public void setDataSource(javax.sql.DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    public BaseResponse insert(Role role) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", role.getName());
        map.put("id_status", role.getId_status());
        String sql = "INSERT INTO roles(name, id_status) VALUES (:name, :id_status)";
        try {
            getTemplate().update(sql, map);
        } catch (DuplicateKeyException d) {
            logger.error("Couldn't insert the role into postgresql", d);
            return baseResponses.duplicateKeyErrorResponse(role.getName());
        } catch (Exception e) {
            logger.error("Couldn't insert the role into postgresql", e);
            return baseResponses.serverErrorResponse();
        }
        return baseResponses.successMessage();
    }

    public NamedParameterJdbcTemplate getTemplate() {
        return namedParameterJdbcTemplate;
    }
}
