package uz.oltinolma.consumer.mvc.service.impl;

import uz.oltinolma.consumer.mvc.dao.TaxonomyDao;
import uz.oltinolma.consumer.mvc.model.ResponseWrapper;
import uz.oltinolma.consumer.mvc.model.Taxonomy;
import uz.oltinolma.consumer.mvc.service.TaxonomyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaxonomyServiceImpl implements TaxonomyService {

    private TaxonomyDao taxonomyDao;

    @Autowired
    public void setTaxonomyDao(TaxonomyDao taxonomyDao) {
        this.taxonomyDao = taxonomyDao;
    }

    @Override
    public void insert(Taxonomy taxonomy) {
        taxonomyDao.insert(taxonomy);
    }

    @Override
    public void update(Taxonomy taxonomy) {
        taxonomyDao.update(taxonomy);
    }

    @Override
    public void delete(Integer id) {
        taxonomyDao.delete(id);
    }

    @Override
    public ResponseWrapper getById(Integer id) {
        return new ResponseWrapper(taxonomyDao.getById(id));
    }

    @Override
    public ResponseWrapper getAll() {
        return new ResponseWrapper(taxonomyDao.getAll());
    }
}
