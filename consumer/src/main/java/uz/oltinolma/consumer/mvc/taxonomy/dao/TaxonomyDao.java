package uz.oltinolma.consumer.mvc.taxonomy.dao;

import uz.oltinolma.consumer.mvc.taxonomy.Taxonomy;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface TaxonomyDao {

    int insert(Taxonomy taxonomy);

    int update(Taxonomy taxonomy);

    int delete(Integer id);

    Taxonomy getById(Integer id);

    List<Taxonomy> getAll();

    Object listForInputLabels();

    List<HashMap<String, Object>>  getListByMovieId(UUID movieId);

    Object getAsHierarchicalStructure(Integer id);

}
