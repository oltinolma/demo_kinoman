package uz.oltinolma.consumer.mvc.movie.dao;


import uz.oltinolma.consumer.mvc.movie.Movie;

import java.util.List;
import java.util.UUID;

public interface MovieDao {

    void insert(String json);
    Object info(UUID id);
    List<Movie> list();

}
