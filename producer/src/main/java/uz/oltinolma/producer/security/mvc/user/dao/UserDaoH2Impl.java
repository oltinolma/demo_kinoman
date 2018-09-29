package uz.oltinolma.producer.security.mvc.user.dao;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.oltinolma.producer.security.common.LogUtil;
import uz.oltinolma.producer.security.mvc.user.User;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoH2Impl extends UserDao {
    private static final Logger logger = LogUtil.getInstance();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    @Qualifier("h2Datasource")
    public void setDataSource(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    @Override
    public BaseResponse insertAll(List<User> users) {
        String sql = "INSERT INTO user(id, name, login, password, id_role, enable) " +
                "VALUES(:id, :name, :login, :password, :id_roles, :enable) ";
        Map<String, Object>[] batch = new HashMap[users.size()];
        for (int i = 0; i < users.size(); i++) {
            User employee = users.get(i);
            Map<String, Object> map = new HashMap<>();
            map.put("id", employee.getId());
            map.put("name", employee.getName());
            map.put("login", employee.getLogin());
            map.put("password", employee.getPassword());
            map.put("id_role", employee.getId_roles());
            map.put("enable", employee.isEnable());
            batch[i] = map;
        }
        try {
            namedParameterJdbcTemplate.batchUpdate(sql, batch);
        } catch (Exception e) {
            logger.error("Couldn't insert users into h2.", e);
            return baseResponses.serverErrorResponse();
        }
        return baseResponses.successMessage();
    }
}
