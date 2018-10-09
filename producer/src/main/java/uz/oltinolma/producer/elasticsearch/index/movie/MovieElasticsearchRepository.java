package uz.oltinolma.producer.elasticsearch.index.movie;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import java.util.UUID;

public interface MovieElasticsearchRepository extends ElasticsearchCrudRepository<Movie, UUID> {

}
