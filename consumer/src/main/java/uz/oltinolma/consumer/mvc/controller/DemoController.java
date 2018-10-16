package uz.oltinolma.consumer.mvc.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.oltinolma.consumer.mvc.model.ResponseWrapper;
import uz.oltinolma.consumer.mvc.movie.dao.MovieDao;
import uz.oltinolma.consumer.mvc.taxonomy.dao.TaxonomyDao;

import java.util.UUID;

@RestController
public class DemoController {

    @Autowired
    private MovieDao dao;

    @Autowired
    private TaxonomyDao taxonomyDao;

    @PostMapping("/movie/insert")
    public ResponseWrapper insert(@RequestBody String json) {
        dao.insert(json.replace("'", "''"));
        return new ResponseWrapper("Succesfull");
    }

    @GetMapping(value = "/get", produces = "application/json;utf-8")
    public @ResponseBody
    Object get() {
        return taxonomyDao.listForInputLabels();
    }

    @GetMapping(value = "/movie/info/{id}", produces = "application/json;utf-8")
    public @ResponseBody
    Object get(@PathVariable("id") UUID uuid) {
        return dao.info(uuid);
    }
}
