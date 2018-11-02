package uz.oltinolma.consumer.mvc.movie.dao;

import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.oltinolma.consumer.mvc.movie.Movie;
import uz.oltinolma.consumer.mvc.taxonomy.service.TaxonomyService;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Repository
public class MovieDaoImpl implements MovieDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private TaxonomyService taxonomyService;

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
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.query(sql, parameterSource, resultSet -> {
            if (resultSet.next())
                return resultSet.getString("get_json_from_movie_id");
            else return null;
        });
    }

    @Override
    public List<Object> list() {
        String sql = "select * from movies order by created_date DESC";
        List<Object> list = new ArrayList<>();
        namedParameterJdbcTemplate.query(sql,(resultSet, i) -> {
            list.add(getMovieAsObject((UUID) resultSet.getObject("id")));
            return null;
        });
        return list;
    }

    @Override
    @Transactional
    public Object getMoviesListFromRequestedTaxonomiesForMenu(List<String> taxonomies) {
        String sql = "select * from view_movie_taxonomy_as_array_agg t where ARRAY[:params ]::text[] <@ ARRAY[t.tag_array]::text[];";
        SqlParameterSource parameterSource = new MapSqlParameterSource("params", taxonomies);
        List<Object> list = new ArrayList<>();
        namedParameterJdbcTemplate.query(sql, parameterSource, (resultSet, i) -> {
            list.add(getMovieAsObject((UUID) resultSet.getObject("id")));
            return null;
        });
        return list;
    }

    @Override
    public Object getMovieAsObject(UUID id) {
        String sql = "select * from movies where id=:id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        Movie movie = namedParameterJdbcTemplate.query(sql, parameterSource, resultSet -> {
            if (resultSet.next()) {
                return MovieExtractor.extract(resultSet);
            }
            return null;
        });
        HashMap<String, Object> map = new HashMap<>();
        map.put("movie", movie);
        map.put("file", getFile(id));
        map.put("taxonomy", taxonomyService.getListByMovieId(id));
        return map;
    }

    @Override
    public Object getListSortedByUploadTime() {
        String sql = "select * from movies";
        SqlParameterSource parameterSource = new MapSqlParameterSource("params", null);
        List<Object> list = new ArrayList<>();
        namedParameterJdbcTemplate.query(sql, parameterSource, (resultSet, i) -> {
            list.add(getMovieAsObject((UUID) resultSet.getObject("id")));
            return null;
        });
        return list;
    }

    @Override
    public Object getMovieListFromRequestedTaxonomies(List<String> taxonomies) {
        String sql = "select * from calculate_movie_comparion_ratio_from_taxonomies(ARRAY[:params ]);";
        SqlParameterSource parameterSource = new MapSqlParameterSource("params", taxonomies);
        List<Object> list = new ArrayList<>();
        namedParameterJdbcTemplate.query(sql, parameterSource, (resultSet, i) -> {
            if (resultSet.getLong("comparison_ratio") > 0) {
                list.add(getMovieAsObject((UUID) resultSet.getObject("id")));
            }
            return null;
        });
        return list;
    }

    private String getFile(UUID id) {
        String sql = "select * from files where id_movie=:id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.query(sql, parameterSource, resultSet -> {
            if (resultSet.next()) {
                return resultSet.getString("standart_absolute_path");
            }
            return null;
        });

    }
}
