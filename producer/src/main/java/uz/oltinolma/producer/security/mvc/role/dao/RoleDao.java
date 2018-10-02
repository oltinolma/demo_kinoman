package uz.oltinolma.producer.security.mvc.role.dao;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import uz.oltinolma.producer.security.common.BaseResponses;
import uz.oltinolma.producer.security.common.LogUtil;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;
import uz.oltinolma.producer.security.mvc.role.Role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class RoleDao {
    private static final Logger logger = LogUtil.getInstance();
    @Autowired
    private RoleExtractor extractor;
    @Autowired
    protected BaseResponses baseResponses;
    public abstract void setDataSource(javax.sql.DataSource dataSource);

    public abstract NamedParameterJdbcTemplate getTemplate();

    public List<Role> getAllRoles() {
        String sql = "SELECT * FROM role";
        return getTemplate().query(sql, (resultSet, i) -> extractor.extract(resultSet));
    }

    public Role getRoleById(int id) {
        String sql = "SELECT * FROM role WHERE id=:id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        return getTemplate().query(sql, parameterSource, resultSet -> {
            if (resultSet.next()) {
                return extractor.extract(resultSet);
            }
            return null;
        });
    }


    /**@Override in order to use */
    public BaseResponse insertAll(List<Role> roles) {
        throw new UnsupportedOperationException();
    }

    /**@Override in order to use */
    public BaseResponse insert(Role role) {
        throw new UnsupportedOperationException();
    }

    public BaseResponse update(Role role) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", role.getId());
        map.put("name", role.getName());
        String sql = "UPDATE role SET name=:name WHERE id=:id";
        try {
            getTemplate().update(sql, map);
        } catch (DuplicateKeyException d) {
            logger.error("duplicate role = " + role.getName(), d);
            return baseResponses.duplicateKeyErrorResponse(role.getName());
        } catch (Exception e) {
            logger.error("Couldn't update the role. ", e);
            return baseResponses.serverErrorResponse();
        }
        return baseResponses.successMessage();
    }

    public BaseResponse delete(int id) {
        String sql = "DELETE FROM role WHERE id=:id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        try {
            getTemplate().update(sql, parameterSource);
        } catch (Exception e) {
            logger.error("Couldn't delete the role.", e);
            return baseResponses.serverErrorResponse();
        }
        return baseResponses.successMessage();
    }


}
