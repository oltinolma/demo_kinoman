package uz.oltinolma.consumer.mvc.movie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.oltinolma.consumer.mvc.movie.dao.MovieDao;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class MovieServiceImpl implements MovieService {


    private MovieDao movieDao;

    @Autowired

    public void setMovieDao(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public String movieInfoById(UUID id) {
        return String.valueOf(movieDao.info(id)).replace("''","'");

    }

    @Override
    public void insert(String json) {
        movieDao.insert( json);
    }

    @Override
    public List<Object> list() {
        return movieDao.list();
    }

    @Override
    public Object getMoviesListFromRequestedTaxonomiesForMenu(List<String> taxonomies) {
        return movieDao.getMoviesListFromRequestedTaxonomiesForMenu(taxonomies);
    }

    @Override
    public HashMap<String, Object> getMovieAsObject(UUID id) {
        return (HashMap<String, Object>) movieDao.getMovieAsObject(id);
    }

    @Override
    public Object getMovieListFromRequestedTaxonomies(List<String> taxonomies) {
        return movieDao.getMovieListFromRequestedTaxonomies(taxonomies);
    }
}
