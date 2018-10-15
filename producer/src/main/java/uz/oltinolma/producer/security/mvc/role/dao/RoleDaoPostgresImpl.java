package uz.oltinolma.producer.security.mvc.role.dao;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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
    private SimpleJdbcInsert roleInsert;

    @Autowired
    @Qualifier("datasource")
    public void setDataSource(javax.sql.DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        roleInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("role")
                .usingGeneratedKeyColumns("id");
    }

    /**
     * @return generated for new record
     */
    public int insert(Role role) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", role.getName());
        map.put("id_status", role.getId_status());
        return roleInsert.executeAndReturnKey(map).intValue();
    }

    public NamedParameterJdbcTemplate getTemplate() {
        return namedParameterJdbcTemplate;
    }
}
