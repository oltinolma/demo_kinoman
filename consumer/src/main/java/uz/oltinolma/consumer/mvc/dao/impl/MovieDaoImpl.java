package uz.oltinolma.consumer.mvc.dao.impl;

import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import uz.oltinolma.consumer.mvc.dao.MovieDao;
import uz.oltinolma.consumer.mvc.model.Movie;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class MovieDaoImpl implements MovieDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    @Override
    public void insert(String json) {
        PGobject jsonObject = new PGobject();
        jsonObject.setType("json");
        try {
            jsonObject.setValue(json);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SqlParameterSource parameterSource = new MapSqlParameterSource("json", jsonObject);
        String sql = "select * from parse_incoming_json(:json)";
        namedParameterJdbcTemplate.query(sql, parameterSource, resultSet -> null);
    }

    @Override
    public Object info(UUID id) {
        String sql = "select get_json_from_movie_id from get_json_from_movie_id(:id)";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id",id);
        return namedParameterJdbcTemplate.query(sql, parameterSource,resultSet -> {
            if(resultSet.next())
                return resultSet.getString("get_json_from_movie_id");
            else return null;
        });
    }

    @Override
    public List<Movie> list() {
        String sql = "select * from movies";
        return namedParameterJdbcTemplate.query(sql, (rs,i)-> {
            Movie m = new Movie();
            m.setId((UUID) rs.getObject("id"));
            m.setName(rs.getString("name"));
            m.setCreatedDate(rs.getLong("created_date"));
            m.setReleasedDate(rs.getDate("release_date"));
            m.setFullName(rs.getString("full_name"));
            m.setAgeRating(rs.getString("age_rating"));
            m.setDescription(rs.getString("description"));
            return m;
        });
    }
}
