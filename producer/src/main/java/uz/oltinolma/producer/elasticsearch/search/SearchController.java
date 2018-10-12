package uz.oltinolma.producer.elasticsearch.search;

import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/temp/search/for")
public class SearchController {
    @Autowired
    private UniversalSearchHelper universalSearchQueries;
    @Autowired
    private TaxonomySearchHelper taxonomySearchHelper;

    @RequestMapping("/movie/or/taxonomy")
    public Object searchMovieOrTaxonomy(@RequestParam String term) {
        return universalSearchQueries.searchForMovieOrTaxonomy(term.toLowerCase());
    }

    @RequestMapping("/taxonomy")
    public Object suggestTaxonomy(@RequestParam String term, @RequestParam String parent) {
        Assert.notNull(term, "term cannot be null");
        Assert.notNull(parent, "parent cannot be null");
        return taxonomySearchHelper.searchForTaxonomy(term.toLowerCase(), parent.toLowerCase());
    }
}
