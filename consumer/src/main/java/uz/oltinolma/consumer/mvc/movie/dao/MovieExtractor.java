package uz.oltinolma.consumer.mvc.movie.dao;

import uz.oltinolma.consumer.mvc.movie.Movie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MovieExtractor {
    public static Movie extract(ResultSet rs) throws SQLException {
        Movie taxonomy = new Movie();
        taxonomy.setId((UUID) rs.getObject("id"));
        taxonomy.setName(rs.getString("name"));
        taxonomy.setAgeRating(rs.getString("age_rating"));
        taxonomy.setFullName(rs.getString("full_name"));
        taxonomy.setDescription(rs.getString("description"));
        taxonomy.setCreatedDate(rs.getLong("created_date"));
        taxonomy.setReleasedDate(rs.getDate("release_date"));
        return taxonomy;
    }
}
