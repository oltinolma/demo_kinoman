package uz.oltinolma.producer.elasticsearch.index.taxonomy;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TaxonomyElasticsearchRepository extends ElasticsearchRepository<Taxonomy, Integer> {
}
