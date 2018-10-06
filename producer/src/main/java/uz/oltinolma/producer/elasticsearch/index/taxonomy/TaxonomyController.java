package uz.oltinolma.producer.elasticsearch.index.taxonomy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.oltinolma.producer.security.common.BaseResponses;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;

@RestController
@RequestMapping("/elastic/taxonomy")
public class TaxonomyController {
    @Autowired
    private TaxonomyService taxonomyService;
    @Autowired
    private BaseResponses baseResponses;

    @PreAuthorize("@SecurityPermission.hasPermission('taxonomy.index')")
    @RequestMapping("/index/all")
    public BaseResponse indexAllMerchants() {
        taxonomyService.indexAll();
        return baseResponses.successMessage();
    }

    @PreAuthorize("@SecurityPermission.hasPermission('taxonomy.index')")
    @PostMapping("/index")
    public BaseResponse index(@RequestBody Taxonomy taxonomy) {
        taxonomyService.index(taxonomy);
        return baseResponses.successMessage();
    }
}
