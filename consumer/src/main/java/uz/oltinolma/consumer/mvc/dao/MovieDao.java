package uz.oltinolma.consumer.mvc.dao;


import uz.oltinolma.consumer.mvc.model.Movie;

import java.util.List;
import java.util.UUID;

public interface MovieDao {

    void insert(String json);
    Object info(UUID id);
    List<Movie> list();

}
