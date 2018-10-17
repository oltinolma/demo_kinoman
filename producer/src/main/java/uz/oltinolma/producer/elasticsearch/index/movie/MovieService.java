package uz.oltinolma.producer.elasticsearch.index.movie;

import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.oltinolma.producer.elasticsearch.index.tools.IndexingTools;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MoviePgRepository postgres;
    @Autowired
    private MovieElasticsearchRepository elasticsearch;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Client client;

    public void index(Movie movie) {
        elasticsearch.save(movie);
    }

    public void indexAll(List<Movie> movies) {
        elasticsearch.saveAll(movies);
    }

    public void indexAll() {
        prepareIndex();
        elasticsearch.saveAll(getAllMerchantsFromPg());
    }

    public List<Movie> getAllMerchantsFromPg() {
        return postgres.getAllMovies();
    }

    private void prepareIndex() {
        try {
            IndexingTools indexingTools = new IndexingTools(restTemplate).setAnalyzerName("autocomplete")
                    .setIndexName("movie_index")
                    .setType("movie")
                    .addField("name")
                    .addField("full_name");
            indexingTools.deleteIndexIfExists();
            indexingTools.indexWithEdge_ngramFilter(client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}