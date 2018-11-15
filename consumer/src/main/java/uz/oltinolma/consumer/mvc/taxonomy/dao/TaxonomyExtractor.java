package uz.oltinolma.consumer.mvc.taxonomy.dao;

import uz.oltinolma.consumer.mvc.taxonomy.Taxonomy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class TaxonomyExtractor {
    public static Taxonomy extract(ResultSet rs) throws SQLException {
        Taxonomy taxonomy = new Taxonomy();
        taxonomy.setId(rs.getInt("id"));
        taxonomy.setName(rs.getString("name"));
        taxonomy.setStructure(rs.getBoolean("structure"));
        taxonomy.setIdParent(rs.getInt("id_parent"));
        return taxonomy;
    }

    public static HashMap<String, Object> extractAsHashMap(ResultSet rs) throws SQLException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("parent", rs.getString("parent_name"));
        map.put("taxonomy", rs.getString("taxonomy_name"));
        return map;
    }
}
