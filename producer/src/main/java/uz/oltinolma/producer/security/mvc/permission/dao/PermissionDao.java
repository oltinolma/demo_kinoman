package uz.oltinolma.producer.security.mvc.permission.dao;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import uz.oltinolma.producer.security.common.BaseResponses;
import uz.oltinolma.producer.security.common.LogUtil;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;
import uz.oltinolma.producer.security.mvc.permission.Permissions;

import javax.sql.DataSource;
import java.util.*;

public abstract class PermissionDao {
    private static final Logger logger = LogUtil.getInstance();
    @Autowired
    private PermissionsExtractor extractor;
    @Autowired
    protected BaseResponses baseResponses;
    @Autowired
    public abstract void setDataSource(DataSource dataSource);
    
    public abstract NamedParameterJdbcTemplate getTemplate();

    public Set<String> getByLogin(String login) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("login", login);
        String sql = "select permission_name from view_permission_login where login=:login";
        return new LinkedHashSet<String>(getTemplate().query(sql, parameterSource, (resultSet, rowNum) -> resultSet.getString("permission_name")));
    }

    public List<Permissions> list() {
        String sql = "SELECT * FROM permissions order by id";
        return getTemplate().query(sql, (resultSet, i) -> extractor.extract(resultSet));
    }

    public Permissions get(int id) {
        String sql = "SELECT * FROM permissions WHERE id=:id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        return this.getTemplate().query(sql, parameterSource, (ResultSetExtractor<Permissions>) resultSet -> {
            if (resultSet.next()) {
                return extractor.extract(resultSet);
            }
            return null;
        });
    }
    /**@Override in order to use */
    public BaseResponse insertAll(List<Permissions> permissions) {
        throw new UnsupportedOperationException();
    }

    /**@Override in order to use */
    public BaseResponse insert(Permissions permissions) {
       throw new UnsupportedOperationException();
    }

    public BaseResponse update(Permissions permissions) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", permissions.getId());
        map.put("name", permissions.getName());
        map.put("info", permissions.getInfo());
        String sql = "UPDATE permissions SET name=:name,info=:info WHERE id=:id";
        BaseResponse baseResponse = new BaseResponse();
        try {
            this.getTemplate().update(sql, map);
        } catch (DuplicateKeyException d) {
            logger.error("Couldn't update the permission.", d);
            return baseResponses.duplicateKeyErrorResponse(permissions.getName());
        } catch (Exception e) {
            logger.error("Couldn't update the permission.", e);
            return baseResponses.serverErrorResponse();
        }
        return baseResponse;
    }

    public BaseResponse delete(int id) {
        String sql = "DELETE FROM permissions WHERE id=:id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        BaseResponse baseResponse = new BaseResponse();

        try {
            this.getTemplate().update(sql, parameterSource);
        } catch (Exception e) {
            logger.error("Couldn't delete the permission.", e);
            return baseResponses.serverErrorResponse();
        }
        return baseResponse;
    }
}
