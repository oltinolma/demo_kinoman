package uz.oltinolma.producer.elasticsearch.index.taxonomy;

import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.oltinolma.producer.elasticsearch.index.tools.IndexingTools;

import java.io.IOException;
import java.util.List;

@Service
public class TaxonomyService {
    @Autowired
    private TaxonomyPgRepository postgres;
    @Autowired
    private TaxonomyElasticsearchRepository elasticsearch;
    @Autowired
    private Client client;


    public void index(Taxonomy taxonomy) {
        elasticsearch.save(taxonomy);
    }

    public void indexAll(List<Taxonomy> taxonomies) {
        elasticsearch.saveAll(taxonomies);
    }

    public void indexAll() {
        try {
            prepareIndex();
            elasticsearch.saveAll(getAllTaxonomiesFromPg());
        } catch (IOException e) {
            e.printStackTrace();
            //TODO react
        }
    }

    public List<Taxonomy> getAllTaxonomiesFromPg() {
        return postgres.getAllTaxonomies();
    }

    private void prepareIndex() throws IOException {
        IndexingTools indexingTools = new IndexingTools.Builder(client)
                .setAnalyzerName("autocomplete")
                .setIndexName("taxonomy_index")
                .setType("taxonomy")
                .addField("name")
                .build();
        indexingTools.deleteIndexIfExists();
        indexingTools.createIndexWithEdge_ngramAnalyzer();
    }
}
