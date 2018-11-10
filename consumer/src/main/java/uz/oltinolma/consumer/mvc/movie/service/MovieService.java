package uz.oltinolma.consumer.mvc.movie.service;

import uz.oltinolma.consumer.mvc.movie.Movie;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface MovieService {

    String movieInfoById(UUID id);

    void insert(String json);

    List<Object> list();

    Object getMoviesListFromRequestedTaxonomiesForMenu(List<String> taxonomies);

    HashMap<String, Object> getMovieAsObject(UUID id);

    Object getMovieListFromRequestedTaxonomies(String taxonomy);
}
