package uz.oltinolma.consumer.mvc.dao;

import uz.oltinolma.consumer.mvc.model.Taxonomy;

import java.util.List;

public interface TaxonomyDao {

    int insert(Taxonomy taxonomy);

    int update(Taxonomy taxonomy);

    int delete(Integer id);

    Taxonomy getById(Integer id);

    List<Taxonomy> getAll();

}
