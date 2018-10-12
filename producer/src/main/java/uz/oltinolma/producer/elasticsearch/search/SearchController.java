package uz.oltinolma.producer.elasticsearch.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/temp/search/for")
public class SearchController {
    @Autowired
    private ActorSearchHelper actorSearchQueries;
    @Autowired
    private UniversalSearchHelper universalSearchQueries;
    @Autowired
    private CountrySearchHelper countrySearchHelper;

    @RequestMapping("/movie/or/taxonomy")
    public Object searchMovieOrTaxonomy(@RequestParam String term) {
        return universalSearchQueries.searchForMovieOrTaxonomy(term.toLowerCase());
    }

    @RequestMapping("/actor")
    public Object suggestActors(@RequestParam String term) {
        return actorSearchQueries.searchForActors(term.toLowerCase());
    }

    @RequestMapping("/country")
    public Object suggestCountries(@RequestParam String term) {
        return countrySearchHelper.searchForCountry(term.toLowerCase());
    }
}
