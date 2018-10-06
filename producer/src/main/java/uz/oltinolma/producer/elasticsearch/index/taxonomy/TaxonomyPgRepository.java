package uz.oltinolma.producer.elasticsearch.index.taxonomy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TaxonomyPgRepository {
    private NamedParameterJdbcTemplate template;

    @Autowired
    public void setTemplate(DataSource datasource) {
        this.template = new NamedParameterJdbcTemplate(datasource);
    }

    public List<Taxonomy> getAllTaxonomies() {
        String sql = "select t.id, t.name, t.id_parent, t.parent_name as taxonomy  from get_all_taxonomy_ancestors() t ";
        return template.query(sql, (rs, i) -> new TaxonomyExtractor().extractData(rs));
    }

    public class TaxonomyExtractor implements ResultSetExtractor<Taxonomy> {
        @Override
        public Taxonomy extractData(ResultSet rs) throws SQLException, DataAccessException {
            Taxonomy m = new Taxonomy();
            m.setId(rs.getInt("id"));
            m.setName(rs.getString("name"));
            m.setId_parent(rs.getInt("id_parent"));
            m.setTaxonomy(rs.getString("taxonomy"));
            return m;
        }
    }
}
