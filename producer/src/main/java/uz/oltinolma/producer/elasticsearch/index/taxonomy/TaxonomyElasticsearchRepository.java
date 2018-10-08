package uz.oltinolma.producer.elasticsearch.index.taxonomy;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

public interface TaxonomyElasticsearchRepository extends ElasticsearchCrudRepository<Taxonomy, Integer> {
}
