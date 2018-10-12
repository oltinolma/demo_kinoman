package uz.oltinolma.producer.elasticsearch.search;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountrySearchHelper extends AbstractSearchHelper {
    public List<SearchResult> searchForCountry(String term) {
        SearchRequest request = searchRequest(countryFuzzySearch(term, "name"), "taxonomy_index");
        ActionFuture<SearchResponse> r = client.search(request);
        return getTopTen(r.actionGet(2000));
    }

    private QueryBuilder countryFuzzySearch(String term, String field) {
        QueryBuilder fuzzy = QueryBuilders.boolQuery()
                .filter(QueryBuilders.matchQuery("taxonomy", "countries"))
                .must(QueryBuilders.fuzzyQuery(field, term).fuzziness(Fuzziness.TWO));
        return fuzzy;
    }
}
