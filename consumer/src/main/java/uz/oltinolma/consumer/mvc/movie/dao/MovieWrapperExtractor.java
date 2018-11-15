package uz.oltinolma.consumer.mvc.movie.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ReferenceType;
import org.postgresql.util.PGobject;
import uz.oltinolma.consumer.mvc.movie.Movie;
import uz.oltinolma.consumer.mvc.movie.MovieWrapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MovieWrapperExtractor {
    public static MovieWrapper extract(ResultSet rs) throws SQLException {
        if (rs.next()) {
            MovieWrapper wrapper = new MovieWrapper();
            Movie movie = new Movie();
            movie.setId((UUID) rs.getObject("id"));
            movie.setName(rs.getString("name"));
            movie.setAgeRating(rs.getString("age_rating"));
            movie.setFullName(rs.getString("full_name"));
            movie.setDescription(rs.getString("description"));
            movie.setCreatedDate(rs.getLong("created_date"));
            movie.setReleasedDate(rs.getDate("release_date"));
            wrapper.setFile(rs.getString("file"));
            try {
                wrapper.setTaxonomy(new ObjectMapper().readValue(rs.getString("taxonomy"),
                        new TypeReference<List<Map<String, Object>>>(){}));
            } catch (IOException e) {
                e.printStackTrace();
            }
            wrapper.setMovie(movie);
            return wrapper;
        }
        return null;
    }
}
