package uz.oltinolma.consumer.mvc.taxonomy.service;

import uz.oltinolma.consumer.mvc.model.ResponseWrapper;
import uz.oltinolma.consumer.mvc.taxonomy.Taxonomy;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface TaxonomyService {

    void insert(Taxonomy taxonomy);

    void update(Taxonomy taxonomy);

    void delete(Integer id);

    ResponseWrapper getById(Integer id);

    ResponseWrapper getAll();

    String listForInputLabels();

    List<HashMap<String, Object>>  getListByMovieId(UUID movieId);
    String getAsHierarchicalStructure(Integer id);

}
