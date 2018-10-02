package uz.oltinolma.producer.security.mvc.permission.dao;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.oltinolma.producer.security.common.LogUtil;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;
import uz.oltinolma.producer.security.mvc.permission.Permissions;

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
    public BaseResponse insertAll(List<Permissions> permissions) {
        String sql = "INSERT INTO permission(id, name, notes) VALUES (:id, :name, :notes)";
        Map<String, Object>[] batch = new HashMap[permissions.size()];
        for (int i = 0; i < permissions.size(); i++) {
            Permissions permission = permissions.get(i);
            Map<String, Object> map = new HashMap<>();
            map.put("id", permission.getId());
            map.put("name", permission.getName());
            map.put("notes", permission.getInfo());
            batch[i] = map;
        }
        try {
            template.batchUpdate(sql, batch);
        } catch (Exception e) {
            logger.error("Couldn't insert permissions into h2.", e);
            return baseResponses.serverErrorResponse();
        }
        return baseResponses.successMessage();
    }

    public List<Permissions> listByLoginPermissionWithTopic(String login, String topic) {
        Map<String, Object> map = new HashMap<>();
        map.put("login", login);
//        map.put("topic", topic);
        String sql = "select * from view_permission_login where permission_name like '%" + topic + "%' and login=:login";
        //TODO cant execute parametred query need to fix
        return template.query(sql, map, (resultSet, i) -> {
            Permissions permissions = new Permissions();
            permissions.setId(resultSet.getInt("permission_id"));
            permissions.setName(resultSet.getString("permission_name"));
            permissions.setInfo(resultSet.getString("permission_notes"));
            return permissions;
        });
    }
}
