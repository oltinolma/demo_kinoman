package uz.oltinolma.consumer.mvc.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.oltinolma.consumer.mvc.dao.MovieDao;
import uz.oltinolma.consumer.mvc.dao.TaxonomyDao;

import java.util.UUID;

@RestController
public class Demo {

    @Autowired
    MovieDao dao;

    @Autowired
    TaxonomyDao taxonomyDao;

    @PostMapping("/insertMovie")
    public ResponseWrapper insert(@RequestBody String json) {
        dao.insert(json.replace("'", "''"));
        return new ResponseWrapper("Succesfull");
    }

    @GetMapping(value = "/get", produces = "application/json;utf-8")
    public @ResponseBody
    Object get() {
        return taxonomyDao.listForInputLabels();
        //return ;
    }

    @GetMapping(value = "/movie/info/{id}", produces = "application/json;utf-8")
    public @ResponseBody
    Object get(@PathVariable("id") UUID uuid) {
        return dao.info(uuid);
        //return ;
    }
}
