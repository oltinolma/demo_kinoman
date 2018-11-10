package uz.oltinolma.consumer.mvc.movie.dao;


import uz.oltinolma.consumer.mvc.movie.Movie;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface MovieDao {

    void insert(String json);

    Object info(UUID id);

    List<Object> list();

    Object getMoviesListFromRequestedTaxonomiesForMenu(List<String> taxonomies);

    Object getMovieAsObject(UUID id);

    Object getListSortedByUploadTime();

    Object getMovieListFromRequestedTaxonomies(List<String> taxonomies,List<String> movieNames);

}
