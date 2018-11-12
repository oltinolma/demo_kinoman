package uz.oltinolma.consumer.mvc.movie.dao;

import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.oltinolma.consumer.mvc.movie.MovieWrapper;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

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
    public List<MovieWrapper> list() {
        String sql = "SELECT * FROM view_movie_list_with_full_info order by created_date DESC";
        return namedParameterJdbcTemplate.query(sql, (resultSet, i) -> MovieWrapperExtractor.extract(resultSet));
    }

    @Override
    @Transactional
    public List<MovieWrapper> getMoviesListFromRequestedTaxonomiesForMenu(List<String> taxonomies) {
        String sql = "select * from view_movie_taxonomy_as_array_agg t where ARRAY[:params ]::text[] <@ ARRAY[t.tag_array]::text[]";
        SqlParameterSource parameterSource = new MapSqlParameterSource("params", taxonomies);
        return namedParameterJdbcTemplate.query(sql, parameterSource, (resultSet, i) -> MovieWrapperExtractor.extract(resultSet));
    }

    @Override
    public MovieWrapper getMovieAsObject(UUID id) {
        String sql = "SELECT * FROM view_movie_list_with_full_info where id=:id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.query(sql, parameterSource, MovieWrapperExtractor::extract);
    }

    @Override
    public List<MovieWrapper> getMovieListFromRequestedTaxonomies(List<String> taxonomies, List<String> movieNames) {
        String sql = "select * from calculate_movie_comparion_ratio_from_taxonomies(ARRAY[:taxonomies ] ::text[], ARRAY[:mnames ]::text[]);";
        Map<String, Object> map = new HashMap<>();
        map.put("taxonomies", taxonomies);
        map.put("mnames", movieNames);
        return namedParameterJdbcTemplate.query(sql, map, (resultSet, i) -> {
            if (resultSet.getLong("comparison_ratio") > 0 || resultSet.getBoolean("exist")) {
                return (getMovieAsObject((UUID) resultSet.getObject("id")));
            }
            return null;
        });
    }

}
