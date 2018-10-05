package uz.oltinolma.producer.security.mvc.permission.dao;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.oltinolma.producer.security.common.LogUtil;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;
import uz.oltinolma.producer.security.mvc.permission.Permissions;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class PermissionDaoPostgresImpl extends PermissionDao {
    private static final Logger logger = LogUtil.getInstance();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Resource(name = "datasource")
    public void setDataSource(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    public BaseResponse insert(Permissions permissions) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", permissions.getId());
        map.put("name", permissions.getName());
        map.put("notes", permissions.getInfo());
        String sql = "INSERT INTO permission(name,notes) VALUES (:name,:notes)";
        try {
            this.getTemplate().update(sql, map);
        } catch (DuplicateKeyException d) {
            logger.error("Couldn't insert tje permission into postgres.", d);
            return baseResponses.duplicateKeyErrorResponse(permissions.getName());
        } catch (Exception e) {
            logger.error("Couldn't insert the permission into postgres.", e);
            return baseResponses.serverErrorResponse();
        }
        return baseResponses.successMessage();
    }

    public NamedParameterJdbcTemplate getTemplate() {
        return namedParameterJdbcTemplate;
    }
}
