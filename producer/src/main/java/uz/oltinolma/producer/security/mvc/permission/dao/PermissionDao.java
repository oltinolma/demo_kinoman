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
import uz.oltinolma.producer.security.mvc.permission.Permission;

import javax.sql.DataSource;
import java.util.*;

public abstract class PermissionDao {
    private static int counter;
    private static final Logger logger = LogUtil.getInstance();
    @Autowired
    private PermissionsExtractor extractor;
    @Autowired
    protected BaseResponses baseResponses;
    @Autowired
    public abstract void setDataSource(DataSource dataSource);
    
    public abstract NamedParameterJdbcTemplate getTemplate();

    public Set<String> getByLogin(String login) {
        System.out.println("GET_BY_LOGIN " + ++counter);
        SqlParameterSource parameterSource = new MapSqlParameterSource("login", login);
        String sql = "select permission_name from view_permission_login where login=:login";
        sql = "SELECT p.name AS permission_name FROM permission p \n" +
                "WHERE p.id IN (SELECT rp.id_permission FROM role_permission rp \n" +
                "WHERE rp.id_role = (SELECT u.id_role FROM users u \n" +
                "WHERE u.login = :login))";
        return new LinkedHashSet<String>(getTemplate().query(sql, parameterSource, (resultSet, rowNum) -> resultSet.getString("permission_name")));
    }

    public List<Permission> list() {
        String sql = "SELECT * FROM permission order by id";
        return getTemplate().query(sql, (resultSet, i) -> extractor.extract(resultSet));
    }

    public Permission get(int id) {
        String sql = "SELECT * FROM permission WHERE id=:id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        return this.getTemplate().query(sql, parameterSource, (ResultSetExtractor<Permission>) resultSet -> {
            if (resultSet.next()) {
                return extractor.extract(resultSet);
            }
            return null;
        });
    }
    /**@Override in order to use */
    public BaseResponse insertAll(List<Permission> permissions) {
        throw new UnsupportedOperationException();
    }

    public abstract int insert(Permission permissions);

    public BaseResponse update(Permission permissions) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", permissions.getId());
        map.put("name", permissions.getName());
        map.put("info", permissions.getInfo());
        String sql = "UPDATE permission SET name=:name,info=:info WHERE id=:id";
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
        String sql = "DELETE FROM permission WHERE id=:id";
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
