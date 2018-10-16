package uz.oltinolma.consumer.mvc.taxonomy.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import uz.oltinolma.consumer.mvc.taxonomy.Taxonomy;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        map.put("structure", taxonomy.isStructure());
        map.put("id_parent", taxonomy.getIdParent());
        String sql = "insert into taxonomy (name,structure,id_parent) values (:name,:structure,:id_parent)";
        return namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public int update(Taxonomy taxonomy) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", taxonomy.getId());
        map.put("name", taxonomy.getName());
        map.put("structure", taxonomy.isStructure());
        map.put("id_parent", taxonomy.getIdParent());
        String sql = "update taxonomy set name=:name,structure=:structure, id_parent=:id_parent where id=:id";
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
        String sql = "select * from taxonomy where id=:id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.query(sql, parameterSource, TaxonomyExtractor::extract);
    }

    @Override
    public List<Taxonomy> getAll() {
        String sql = "select * from taxonomy ";
        return namedParameterJdbcTemplate.query(sql, (resultSet, i) -> TaxonomyExtractor.extract(resultSet));
    }

    @Override
    public Object listForInputLabels() {
        String sql = "select array_to_json(array_agg(row_to_json(t))) as x " +
                " from taxonomy t where id_parent is null and structure is true;";
        return namedParameterJdbcTemplate.query(sql, resultSet -> {
            if(resultSet.next())
            return resultSet.getString("x");
            else return null;
        });
    }
}
