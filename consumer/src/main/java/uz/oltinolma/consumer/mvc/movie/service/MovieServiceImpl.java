package uz.oltinolma.consumer.mvc.movie.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uz.oltinolma.consumer.mvc.config.SearchResult;
import uz.oltinolma.consumer.mvc.movie.dao.MovieDao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private RestTemplate restTemplate;

    private MovieDao movieDao;

    @Autowired

    public void setMovieDao(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public String movieInfoById(UUID id) {
        return String.valueOf(movieDao.info(id)).replace("''", "'");

    }

    @Override
    public void insert(String json) {
        movieDao.insert(json);
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
    public Object getMovieListFromRequestedTaxonomies(String taxonomy) {
        String resourceUrl
                = "http://192.168.0.50:7576/search/for/movie/or/taxonomy";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(resourceUrl)
                .queryParam("term", taxonomy);
        ResponseEntity<List<SearchResult>> response = restTemplate.exchange(
                builder.build().toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<SearchResult>>() {
                });
        List<String> taxonomies = response.getBody().stream().filter(t -> "taxonomy".equals(t.getType())).map(SearchResult::getTerm).collect(Collectors.toList());
        List<String> movies = response.getBody().stream().filter(t -> "movie".equals(t.getType())).map(SearchResult::getTerm).collect(Collectors.toList());
        return movieDao.getMovieListFromRequestedTaxonomies(taxonomies, movies);
    }
}
