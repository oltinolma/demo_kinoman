package uz.oltinolma.producer.elasticsearch.index.movie;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface MovieElasticsearchRepository extends ElasticsearchRepository<Movie, UUID> {

}
