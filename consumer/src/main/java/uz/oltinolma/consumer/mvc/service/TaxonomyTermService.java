package uz.oltinolma.consumer.mvc.service;

import uz.oltinolma.consumer.mvc.model.ResponseWrapper;
import uz.oltinolma.consumer.mvc.model.TaxonomyTerm;

public interface TaxonomyTermService {

    void insert(TaxonomyTerm taxonomyTerm);

    void update(TaxonomyTerm taxonomyTerm);

    void delete(Integer id);

    ResponseWrapper getById(Integer id);

    ResponseWrapper getAll();

}
