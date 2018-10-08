package uz.oltinolma.producer.elasticsearch.search;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UniversalSearchHelper extends AbstractSearchHelper {
    public List<SearchResult> searchForMovieOrTaxonomy(String term) {
        List<SearchResult> results = ngramMatchSearchForMovieOrTaxonomy(term);
        if (results.isEmpty()) {
            term = ifMultipleWordsChooseLongestOne(term);
            results = fuzzySearchForMovieOrTaxonomy(term);
        }

        return results;
    }

    private String ifMultipleWordsChooseLongestOne(String term) {
        String[] words = term.trim().split("\\s+");
        if (words.length > 1) {
            term = Arrays.asList(words)
                    .stream()
                    .max((s1, s2) -> s2.length() - s1.length()).get();
        }
        return term;
    }

    private List<SearchResult> ngramMatchSearchForMovieOrTaxonomy(String term) {
        MultiSearchRequest request = new MultiSearchRequest();
        request.add(searchRequest(matchQueryForMovie(term), "movie_index"));
        request.add(searchRequest(QueryBuilders.matchQuery("name", term), "taxonomy_index"));
        ActionFuture<MultiSearchResponse> r = client.multiSearch(request);
        return getTopTen(r.actionGet(2000));
    }

    private List<SearchResult> fuzzySearchForMovieOrTaxonomy(String term) {
        MultiSearchRequest request = new MultiSearchRequest();
        request.add(searchRequest(fuzzyQueryForMovie(term), "movie_index"));
        request.add(searchRequest(fuzzyQuery("name", term), "taxonomy_index"));
        ActionFuture<MultiSearchResponse> r = client.multiSearch(request);
        return getTopTen(r.actionGet(2000));
    }

    private QueryBuilder matchQueryForMovie(String name) {
        QueryBuilder matchQuery = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("name", name))
                .should(QueryBuilders.matchQuery("full_name", name));

        return matchQuery;
    }

    private QueryBuilder fuzzyQueryForMovie(String name) {
        QueryBuilder boolQuery = QueryBuilders.boolQuery()
                .should(QueryBuilders.fuzzyQuery("name", name))
                .should(QueryBuilders.fuzzyQuery("full_name", name));

        return boolQuery;
    }
}
