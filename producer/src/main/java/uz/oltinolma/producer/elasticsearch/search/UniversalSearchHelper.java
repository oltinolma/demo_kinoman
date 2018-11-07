package uz.oltinolma.producer.elasticsearch.search;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversalSearchHelper extends AbstractSearchHelper {
    public List<SearchResult> searchForMovieOrTaxonomy(String term) {
        List<SearchResult> results = ngramMatchSearchForMovieOrTaxonomy(term);
        System.out.println("ngram " + results);
        if (results.isEmpty()) {
            term = ifMultipleWordsChooseLongestOne(term);
            results = fuzzySearchForMovieOrTaxonomy(term);
            System.out.println("fuzzy " + results);
        }

        return results;
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

    private QueryBuilder matchQueryForMovie(String term) {
        QueryBuilder matchQuery = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("name", term))
                .should(QueryBuilders.matchQuery("full_name", term));

        return matchQuery;
    }

    private QueryBuilder fuzzyQueryForMovie(String name) {
        QueryBuilder boolQuery = QueryBuilders.boolQuery()
                .should(fuzzyQuery("name", name))
                .should(fuzzyQuery("full_name", name));

        return boolQuery;
    }
}
