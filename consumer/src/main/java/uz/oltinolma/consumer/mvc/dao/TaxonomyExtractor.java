package uz.oltinolma.consumer.mvc.dao;

import uz.oltinolma.consumer.mvc.model.Taxonomy;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaxonomyExtractor {
    public static Taxonomy extract(ResultSet rs) throws SQLException {
        Taxonomy taxonomy = new Taxonomy();
        taxonomy.setId(rs.getInt("id"));
        taxonomy.setName(rs.getString("name"));
        taxonomy.setIdStatus(rs.getInt("id_status"));
        taxonomy.setHierarchical(rs.getBoolean("hierarchical"));
        return taxonomy;
    }
}
