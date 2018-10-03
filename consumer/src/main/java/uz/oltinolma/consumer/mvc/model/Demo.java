package uz.oltinolma.consumer.mvc.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.oltinolma.consumer.mvc.dao.TaxonomyTermDao;

@RestController
public class Demo {

    @Autowired
    TaxonomyTermDao dao;

    @GetMapping(value = "/childs")
    public Object demo(@RequestParam("param") String param) {
        return dao.getChildsAsJson(param);
    }
    @GetMapping(value = "/parents")
    public Object get(@RequestParam("param") String param) {
        return dao.getParentsAsJson(param);
    }

}
