package uz.oltinolma.consumer.mvc.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import uz.oltinolma.consumer.mvc.dao.TaxonomyTermDao;
import uz.oltinolma.consumer.mvc.dao.TaxonomyTermExtractor;
import uz.oltinolma.consumer.mvc.model.TaxonomyTerm;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TaxonomyTermDaoImpl implements TaxonomyTermDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public int insert(TaxonomyTerm taxonomyTerm) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", taxonomyTerm.getName());
        map.put("id_parent", taxonomyTerm.getIdParent());
        map.put("id_taxonomy", taxonomyTerm.getIdTaxonomy());
        String sql = "insert into taxonomy_terms (name,id_parent,id_taxonomy) values (:name,:id_parent,:id_taxonomy)";
        return namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public int update(TaxonomyTerm taxonomyTerm) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", taxonomyTerm.getId());
        map.put("name", taxonomyTerm.getName());
        map.put("id_parent", taxonomyTerm.getIdParent());
        map.put("id_taxonomy", taxonomyTerm.getIdTaxonomy());
        String sql = "update taxonomy_terms set name=:name,id_parent=:id_parent,id_taxonomy=:id_taxonomy  where id=:id";
        return namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public int delete(Integer id) {
        String sql = "delete from taxonomy_terms where id =:id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.update(sql, parameterSource);
    }

    @Override
    public TaxonomyTerm getById(Integer id) {
        String sql = "select * from taxonomy_terms where id=:id and id_status=1";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.query(sql, parameterSource, TaxonomyTermExtractor::extract);
    }

    @Override
    public List<TaxonomyTerm> getAll() {
        String sql = "select * from taxonomy_terms where id_status = 1";
        return namedParameterJdbcTemplate.query(sql, (resultSet, i) -> TaxonomyTermExtractor.extract(resultSet));
    }

    @Override
    public String getChildsAsJson(String name) {
        System.out.println("param : " + name);
        String sql = "select get_childs_as_json from get_childs_as_json(:param)";
        SqlParameterSource parameterSource = new MapSqlParameterSource("param", name);
        return namedParameterJdbcTemplate.query(sql, parameterSource, resultSet -> resultSet.next() ? resultSet.getString("get_childs_as_json") : null);
    }

    @Override
    public String getParentsAsJson(String name) {
        System.out.println("param : " + name);
        String sql = "select get_parents_as_json from get_parents_as_json(:param)";
        SqlParameterSource parameterSource = new MapSqlParameterSource("param", name);
        return namedParameterJdbcTemplate.query(sql, parameterSource, resultSet -> resultSet.next() ? resultSet.getString("get_parents_as_json") : null);
    }
}
