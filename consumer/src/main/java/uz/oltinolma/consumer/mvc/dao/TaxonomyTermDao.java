package uz.oltinolma.consumer.mvc.dao;

import uz.oltinolma.consumer.mvc.model.TaxonomyTerm;

import java.util.List;

public interface TaxonomyTermDao {

    int insert(TaxonomyTerm taxonomyTerm);

    int update(TaxonomyTerm taxonomyTerm);

    int delete(Integer id);

    TaxonomyTerm getById(Integer id);

    List<TaxonomyTerm> getAll();

    String getChildsAsJson(String name);
    String getParentsAsJson(String name);

}
