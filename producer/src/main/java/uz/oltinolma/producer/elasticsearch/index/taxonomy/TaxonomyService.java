package uz.oltinolma.producer.elasticsearch.index.taxonomy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.oltinolma.producer.elasticsearch.index.tools.IndexCreator;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class TaxonomyService {
    @Autowired
    private TaxonomyPgRepository postgres;
    @Autowired
    private TaxonomyElasticsearchRepository elasticsearch;
    @Autowired
    private RestTemplate restTemplate;

    private void deleteIndex() throws URISyntaxException {
        URI uri = indexURL();
        restTemplate.delete(uri);
    }

    private void createIndex() throws URISyntaxException {
        restTemplate.put(indexURL(), indexWithEdge_ngramFilter());
    }

    private void test() {
        try {
            new IndexCreator(restTemplate).setAnalyzerName("autocomplete")
                    .setIndexName("movie_index")
                    .setType("movie")
                    .addField("name")
                    .addField("full_name").create();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private String indexWithEdge_ngramFilter() {
        String json = "{\n" +
                "  \"settings\": {\n" +
                "    \"analysis\": {\n" +
                "      \"filter\": {\n" +
                "        \"autocomplete_filter\": {\n" +
                "          \"type\": \"edge_ngram\",\n" +
                "          \"min_gram\": 1,\n" +
                "          \"max_gram\": 20\n" +
                "        }\n" +
                "      },\n" +
                "      \"analyzer\": {\n" +
                "        \"autocomplete\": { \n" +
                "          \"type\": \"custom\",\n" +
                "          \"tokenizer\": \"standard\",\n" +
                "          \"filter\": [\n" +
                "            \"lowercase\",\n" +
                "            \"autocomplete_filter\"\n" +
                "          ]\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"mappings\": {\n" +
                "    \"taxonomy\": {\n" +
                "      \"properties\": {\n" +
                "        \"name\": {\n" +
                "          \"type\": \"text\",\n" +
                "          \"analyzer\": \"autocomplete\", \n" +
                "          \"search_analyzer\": \"standard\" \n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
        return json;
    }

    private URI indexURL() throws URISyntaxException {
        String protocol = "http://";
        String host = System.getenv("kinoman.host");
        int port = 9200;
        String path = "/taxonomy_index";
        return new URI(protocol + host + ":" + port + path);
    }

    public void index(Taxonomy taxonomy) {
        elasticsearch.save(taxonomy);
    }

    public void indexAll(List<Taxonomy> taxonomies) {
        elasticsearch.saveAll(taxonomies);
    }

    public void indexAll() {
        try {
            deleteIndex();
            createIndex();
            test();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        elasticsearch.saveAll(getAllTaxonomiesFromPg());
    }

    public List<Taxonomy> getAllTaxonomiesFromPg() {
        return postgres.getAllTaxonomies();
    }
}
