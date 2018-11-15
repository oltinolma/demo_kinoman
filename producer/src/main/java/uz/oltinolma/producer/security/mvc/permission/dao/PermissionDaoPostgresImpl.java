package uz.oltinolma.producer.security.mvc.permission.dao;

import org.slf4j.Logger;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import uz.oltinolma.producer.security.common.LogUtil;
import uz.oltinolma.producer.security.mvc.permission.Permission;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class PermissionDaoPostgresImpl extends PermissionDao {
    private static final Logger logger = LogUtil.getInstance();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertPermission;

    @Resource(name = "datasource")
    public void setDataSource(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        insertPermission = new SimpleJdbcInsert(dataSource)
                .withTableName("permission")
                .usingGeneratedKeyColumns("id");
    }

    /**
     * @return int generated id for the record
     */
    public int insert(Permission permissions) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", permissions.getName());
        map.put("notes", permissions.getNotes());

        return insertPermission.executeAndReturnKey(map).intValue();
    }

    public NamedParameterJdbcTemplate getTemplate() {
        return namedParameterJdbcTemplate;
    }
}
