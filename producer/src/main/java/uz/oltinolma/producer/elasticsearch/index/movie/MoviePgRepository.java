package uz.oltinolma.producer.elasticsearch.index.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class MoviePgRepository {
    private NamedParameterJdbcTemplate template;

    @Autowired
    public void setTemplate(DataSource datasource) {
        this.template = new NamedParameterJdbcTemplate(datasource);
    }

    public List<Movie> getAllMovies() {
        String sql = "SELECT id, name, full_name FROM movies";
        return template.query(sql, (rs, i) -> new MovieExtractor().extractData(rs));
    }

    public class MovieExtractor implements ResultSetExtractor<Movie> {
        @Override
        public Movie extractData(ResultSet rs) throws SQLException, DataAccessException {
            Movie m = new Movie();
            m.setId((UUID) rs.getObject("id"));
            m.setName(rs.getString("name"));
            m.setFull_name(rs.getString("full_name"));
            return m;
        }
    }
}
