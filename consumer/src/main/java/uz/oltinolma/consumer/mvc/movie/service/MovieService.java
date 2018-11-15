package uz.oltinolma.consumer.mvc.movie.service;

import uz.oltinolma.consumer.mvc.movie.MovieWrapper;

import java.util.List;
import java.util.UUID;

public interface MovieService {

    void insert(String json);

    List<MovieWrapper> list();

    List<MovieWrapper> getMoviesListFromRequestedTaxonomiesForMenu(List<String> taxonomies);

    MovieWrapper getMovieAsObject(UUID id);

    List<MovieWrapper> getMovieListFromRequestedTaxonomies(String taxonomy);
}
