package uz.oltinolma.producer.security.mvc.permission.dao;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.oltinolma.producer.common.LogUtil;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;
import uz.oltinolma.producer.security.mvc.permission.Permission;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PermissionDaoH2Impl extends PermissionDao {
    private static final Logger logger = LogUtil.getInstance();
    private NamedParameterJdbcTemplate template;

    @Autowired
    @Qualifier("h2Datasource")
    public void setDataSource(DataSource dataSource) {
        template = new NamedParameterJdbcTemplate(dataSource);
    }

    public NamedParameterJdbcTemplate getTemplate() {
        return template;
    }

    @Override
    public void insertAll(List<Permission> permissions) {
        String sql = "INSERT INTO permission(id, name, notes) VALUES (:id, :name, :notes)";
        Map<String, Object>[] batch = new HashMap[permissions.size()];
        for (int i = 0; i < permissions.size(); i++) {
            Permission permission = permissions.get(i);
            Map<String, Object> map = new HashMap<>();
            map.put("id", permission.getId());
            map.put("name", permission.getName());
            map.put("notes", permission.getNotes());
            batch[i] = map;
        }
        try {
            template.batchUpdate(sql, batch);
        } catch (Exception e) {
            logger.error("Couldn't insert permissions into h2.", e);
            throw new RuntimeException("Couldn't insert permissions into h2.", e);
        }
    }

    public int insert(Permission permissions) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", permissions.getId());
        map.put("name", permissions.getName());
        map.put("notes", permissions.getNotes());
        String sql = "INSERT INTO permission(id, name,notes) VALUES (:id,:name,:notes)";

        return this.getTemplate().update(sql, map);
    }
}
