package uz.oltinolma.producer.security.mvc.user.dao;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.oltinolma.producer.security.common.LogUtil;
import uz.oltinolma.producer.security.mvc.user.User;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserDaoPostgresImpl extends UserDao {
    private static final Logger logger = LogUtil.getInstance();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(javax.sql.DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    public BaseResponse insert(User employee) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", employee.getName());
        map.put("login", employee.getLogin());
        map.put("id_role", employee.getId_roles());
        map.put("enable", employee.isEnable());
        map.put("password", employee.getPassword());
        String sql = "INSERT INTO user(name,login,id_role,enable,password) VALUES (:name,:login,:id_role,:enable,:password)";
        try {
            this.namedParameterJdbcTemplate.update(sql, map);
        } catch (DuplicateKeyException d) {
            logger.error("Couldn't insert the employee into postgresql", d);
            return baseResponses.duplicateKeyErrorResponse(employee.getLogin());
        } catch (Exception e) {
            logger.error("Couldn't insert the employee into postgresql", e);
            return baseResponses.serverErrorResponse();
        }
        return baseResponses.successMessage();
    }


}
