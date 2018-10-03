package uz.oltinolma.consumer.mvc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.oltinolma.consumer.mvc.dao.TaxonomyTermDao;
import uz.oltinolma.consumer.mvc.model.ResponseWrapper;
import uz.oltinolma.consumer.mvc.model.TaxonomyTerm;
import uz.oltinolma.consumer.mvc.service.TaxonomyTermService;

@Service
public class TaxonomyTermServiceImpl implements TaxonomyTermService {

    private TaxonomyTermDao taxonomyTermDao;

    @Autowired
    public void setTaxonomyDao(TaxonomyTermDao taxonomyTermDao) {
        this.taxonomyTermDao = taxonomyTermDao;
    }

    @Override
    public void insert(TaxonomyTerm taxonomy) {
        taxonomyTermDao.insert(taxonomy);
    }

    @Override
    public void update(TaxonomyTerm taxonomy) {
        taxonomyTermDao.update(taxonomy);
    }

    @Override
    public void delete(Integer id) {
        taxonomyTermDao.delete(id);
    }

    @Override
    public ResponseWrapper getById(Integer id) {
        return new ResponseWrapper(taxonomyTermDao.getById(id));
    }

    @Override
    public ResponseWrapper getAll() {
        return new ResponseWrapper(taxonomyTermDao.getAll());
    }
}
