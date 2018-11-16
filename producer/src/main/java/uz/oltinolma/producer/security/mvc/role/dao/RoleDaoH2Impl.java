package uz.oltinolma.producer.security.mvc.role.dao;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.oltinolma.producer.common.LogUtil;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;
import uz.oltinolma.producer.security.mvc.role.Role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("roleDaoH2Impl")
public class RoleDaoH2Impl extends RoleDao {
    private static final Logger logger = LogUtil.getInstance();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Qualifier("h2Datasource")
    @Autowired
    public void setDataSource(javax.sql.DataSource h2Datasource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(h2Datasource);
    }

    public NamedParameterJdbcTemplate getTemplate() {
        return namedParameterJdbcTemplate;
    }

    public BaseResponse insertAll(List<Role> roles) {
        String sql = "INSERT INTO role(id, name) VALUES (:id, :name)";
        Map<String, Object>[] batch = new HashMap[roles.size()];
        for (int i = 0; i < roles.size(); i++) {
            Role role = roles.get(i);
            Map<String, Object> map = new HashMap<>();
            map.put("id", role.getId());
            map.put("name", role.getName());
            batch[i] = map;
        }
        try {
            namedParameterJdbcTemplate.batchUpdate(sql, batch);
        } catch (Exception e) {
            logger.error("Couldn't insert roles into h2.", e);
            return baseResponses.serverErrorResponse();
        }
        return baseResponses.successMessage();
    }

    /**
     * @return number of affected rows
     */
    public int insert(Role role) {
        String sql = "INSERT INTO role(id, name) VALUES(:id, :name)";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", role.getName());
        map.put("id", role.getId());
        return getTemplate().update(sql, map);
    }
}
