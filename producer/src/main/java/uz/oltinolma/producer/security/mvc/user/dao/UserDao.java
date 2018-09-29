package uz.oltinolma.producer.security.mvc.user.dao;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import uz.oltinolma.producer.security.common.BaseResponses;
import uz.oltinolma.producer.security.common.LogUtil;
import uz.oltinolma.producer.security.mvc.user.User;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;

import java.util.*;

public abstract class UserDao {
    private static final Logger logger = LogUtil.getInstance();
    @Autowired
    protected BaseResponses baseResponses;
    @Autowired
    public abstract void setDataSource(javax.sql.DataSource dataSource);
    public abstract NamedParameterJdbcTemplate getNamedParameterJdbcTemplate();

    public User findByLogin(String login) {
        String sql = "SELECT * FROM view_user WHERE login=:login";
        SqlParameterSource namedParameters = new MapSqlParameterSource("login", login);
        return getNamedParameterJdbcTemplate().query(sql, namedParameters, rs -> {
            if (rs.next()) {
                return UserExtractor.extract(rs);
            }
            return null;
        });
    }

    public List<User> list() {
        String sql = "SELECT * FROM view_user";
        return getNamedParameterJdbcTemplate().query(sql, (rs, i) -> UserExtractor.extract(rs));
    }

    public User get(UUID id) {
        String sql = "SELECT * FROM view_user WHERE id=:id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        return getNamedParameterJdbcTemplate().query(sql, parameterSource, resultSet -> {
            if (resultSet.next()) {
                return UserExtractor.extract(resultSet);
            }
            return null;
        });
    }

    public BaseResponse delete(int id) {
        String sql = "DELETE FROM user WHERE id=:id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        try {
            getNamedParameterJdbcTemplate().update(sql, parameterSource);
        } catch (Exception e) {
            logger.error("Couldn't delete the employee", e);
            return baseResponses.serverErrorResponse();
        }
        return baseResponses.successMessage();
    }

    /**Override in order to use */
    public BaseResponse insertAll(List<User> employees) {
        throw new UnsupportedOperationException();
    }

    /**Override in order to use */
    public BaseResponse insert(User employee) {
        throw new UnsupportedOperationException();
    }

    public BaseResponse update(User employee) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", employee.getId());
        map.put("name", employee.getName());
        map.put("login", employee.getLogin());
        map.put("id_role", employee.getId_roles());
        map.put("enable", employee.isEnable());
        String sql = "UPDATE user SET name=:name,login=:login,id_role=:id_role,enable=:enable WHERE id=:id";
        try {
            getNamedParameterJdbcTemplate().update(sql, map);
        } catch (DuplicateKeyException d) {
            logger.error("Couldn't update the employee.", d);
            return baseResponses.duplicateKeyErrorResponse(employee.getLogin());
        } catch (Exception e) {
            logger.error("Couldn't update the employee.", e);
            return baseResponses.serverErrorResponse();
        }
        return baseResponses.successMessage();
    }

    public BaseResponse updatePassword(String login, String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("password", password);
        map.put("login", login);
        String sql = "UPDATE user SET password=:password WHERE login=:login";

        try {
            getNamedParameterJdbcTemplate().update(sql, map);
        } catch (Exception e) {
            logger.error("Couldn't update the password for the login = " + login, e);
            return baseResponses.serverErrorResponse();
        }
        return baseResponses.successMessage();
    }

    public List<String> getEmployeeInfoByLogin(String login) {
        String sql = "SELECT e_name,r_name FROM view_info WHERE login=:login";
        SqlParameterSource namedParameters = new MapSqlParameterSource("login", login);
        return getNamedParameterJdbcTemplate().query(sql, namedParameters, rs -> {
            if (rs.next()) {
                List<String> info = new ArrayList<>();
                info.add(rs.getString("e_name"));
                info.add(rs.getString("r_name"));
                return info;
            }
            return null;
        });
    }
}
