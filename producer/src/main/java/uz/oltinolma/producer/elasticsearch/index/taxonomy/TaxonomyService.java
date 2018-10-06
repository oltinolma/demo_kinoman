package uz.oltinolma.producer.elasticsearch.index.taxonomy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxonomyService {
    @Autowired
    private TaxonomyPgRepository postgres;
    @Autowired
    private TaxonomyElasticsearchRepository elasticsearch;

    public void index(Taxonomy taxonomy) {
        elasticsearch.save(taxonomy);
    }

    public void indexAll(List<Taxonomy> taxonomies) {
        elasticsearch.saveAll(taxonomies);
    }

    public void indexAll() {
        elasticsearch.saveAll(getAllTaxonomiesFromPg());
    }

    public List<Taxonomy> getAllTaxonomiesFromPg() {
        return postgres.getAllTaxonomies();
    }
}
