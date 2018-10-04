package uz.oltinolma.consumer.mvc.dao;

import uz.oltinolma.consumer.mvc.model.Taxonomy;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaxonomyExtractor {
    public static Taxonomy extract(ResultSet rs) throws SQLException {
        Taxonomy employee = new Taxonomy();
        employee.setId(rs.getInt("id"));
        employee.setName(rs.getString("name"));
        employee.setIdStatus(rs.getInt("id_status"));
        return employee;
    }
}
