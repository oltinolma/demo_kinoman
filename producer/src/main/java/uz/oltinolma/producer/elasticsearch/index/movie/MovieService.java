package uz.oltinolma.producer.elasticsearch.index.movie;

import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.oltinolma.producer.elasticsearch.index.tools.IndexingTools;

import java.io.IOException;
import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MoviePgRepository postgres;
    @Autowired
    private MovieElasticsearchRepository elasticsearch;
    @Autowired
    private Client client;

    public void index(Movie movie) {
        elasticsearch.save(movie);
    }

    public void indexAll(List<Movie> movies) {
        elasticsearch.saveAll(movies);
    }

    public void indexAll() {
        try {
            prepareIndex();
            elasticsearch.saveAll(getAllMerchantsFromPg());
        } catch (Exception e) {
            //TODO react to exception
            e.printStackTrace();
        }
    }

    public List<Movie> getAllMerchantsFromPg() {
        return postgres.getAllMovies();
    }

    private void prepareIndex() throws IOException {
        IndexingTools indexingTools = new IndexingTools.Builder(client)
                .setAnalyzerName("autocomplete")
                .setIndexName("movie_index")
                .setType("movie")
                .addField("name")
                .addField("full_name")
                .build();

        indexingTools.deleteIndexIfExists();
        indexingTools.createIndexWithEdge_ngramAnalyzer();
    }
}