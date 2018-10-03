package uz.oltinolma.consumer.mvc.dao.impl;

import uz.oltinolma.consumer.mvc.dao.TaxonomyDao;
import uz.oltinolma.consumer.mvc.dao.TaxonomyExtractor;
import uz.oltinolma.consumer.mvc.model.Taxonomy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TaxonomyDaoImpl implements TaxonomyDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public int insert(Taxonomy taxonomy) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", taxonomy.getName());
        map.put("hierarchical", taxonomy.isHierarchical());
        String sql = "insert into taxonomy (name,hierarchical) values (:name,:hierarchical)";
        return namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public int update(Taxonomy taxonomy) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", taxonomy.getId());
        map.put("name", taxonomy.getName());
        map.put("hierarchical", taxonomy.isHierarchical());
        String sql = "update taxonomy set name=:name,hierarchical=:hierarchical  where id=:id";
        return namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public int delete(Integer id) {
        String sql = "delete from taxonomy where id =:id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.update(sql, parameterSource);
    }

    @Override
    public Taxonomy getById(Integer id) {
        String sql = "select * from taxonomy where id=:id and id_status=1";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.query(sql, parameterSource, TaxonomyExtractor::extract);
    }

    @Override
    public List<Taxonomy> getAll() {
        String sql = "select * from taxonomy where id_status = 1";
        return namedParameterJdbcTemplate.query(sql, (resultSet, i) -> TaxonomyExtractor.extract(resultSet));
    }
}
