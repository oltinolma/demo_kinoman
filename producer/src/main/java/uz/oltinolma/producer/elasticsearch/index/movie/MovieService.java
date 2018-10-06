package uz.oltinolma.producer.elasticsearch.index.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MoviePgRepository postgres;
    @Autowired
    private MovieElasticsearchRepository elasticsearch;

    public void index(Movie movie) {
        elasticsearch.save(movie);
    }

    public void indexAll(List<Movie> movies) {
        elasticsearch.saveAll(movies);
    }

    public void indexAll() {
        elasticsearch.saveAll(getAllMerchantsFromPg());
    }

    public List<Movie> getAllMerchantsFromPg() {
        return postgres.getAllMovies();
    }

}