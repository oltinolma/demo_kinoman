package uz.oltinolma.consumer.mvc.service;

import uz.oltinolma.consumer.mvc.model.ResponseWrapper;
import uz.oltinolma.consumer.mvc.model.Taxonomy;

import java.util.List;

public interface TaxonomyService {

    void insert(Taxonomy taxonomy);

    void update(Taxonomy taxonomy);

    void delete(Integer id);

    ResponseWrapper getById(Integer id);

    ResponseWrapper getAll();

    String listForInputLabels();

}
