package uz.oltinolma.consumer.mvc.dao;

import uz.oltinolma.consumer.mvc.model.TaxonomyTerm;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaxonomyTermExtractor {
    public static TaxonomyTerm extract(ResultSet rs) throws SQLException {
        TaxonomyTerm taxonomyTerm = new TaxonomyTerm();
        taxonomyTerm.setId(rs.getInt("id_taxonomy_term"));
        taxonomyTerm.setName(rs.getString("taxonomy_term_name"));
        taxonomyTerm.setTaxonomy_name(rs.getString("taxonomy_name"));
        taxonomyTerm.setIdParent(rs.getInt("id_parent"));
        taxonomyTerm.setIdTaxonomy(rs.getInt("id_taxonomy"));
        taxonomyTerm.setIdStatus(rs.getInt("id_status"));
        taxonomyTerm.setHierarchical(rs.getBoolean("hierarchical"));
        return taxonomyTerm;
    }
}
