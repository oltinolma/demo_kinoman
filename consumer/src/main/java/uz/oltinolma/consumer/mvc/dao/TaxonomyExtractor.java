package uz.oltinolma.consumer.mvc.dao;

import uz.oltinolma.consumer.mvc.model.Taxonomy;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaxonomyExtractor {
    public static Taxonomy extract(ResultSet rs) throws SQLException {
        Taxonomy taxonomy = new Taxonomy();
        taxonomy.setId(rs.getInt("id"));
        taxonomy.setName(rs.getString("name"));
        taxonomy.setStructure(rs.getBoolean("structure"));
        taxonomy.setIdParent(rs.getInt("id_parent"));
        return taxonomy;
    }
}
