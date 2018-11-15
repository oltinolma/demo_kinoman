package uz.oltinolma.consumer.mvc.movie.dao;

import uz.oltinolma.consumer.mvc.movie.MovieWrapper;

import java.util.List;
import java.util.UUID;

public interface MovieDao {

    void insert(String json);

    List<MovieWrapper> list();

    List<MovieWrapper> getMoviesListFromRequestedTaxonomiesForMenu(List<String> taxonomies);

    MovieWrapper getMovieAsObject(UUID id);

    List<MovieWrapper> getMovieListFromRequestedTaxonomies(List<String> taxonomies,List<String> movieNames);

}
