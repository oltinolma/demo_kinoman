package uz.oltinolma.consumer.mvc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.oltinolma.consumer.mvc.dao.MovieDao;
import uz.oltinolma.consumer.mvc.service.MovieService;

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
}
