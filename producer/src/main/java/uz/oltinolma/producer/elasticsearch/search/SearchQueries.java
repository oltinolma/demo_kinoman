package uz.oltinolma.producer.elasticsearch.search;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchQueries {
    @Autowired
    private TransportClient client;

    public List<SearchResult> fuzzySearchByActorOrMovie(String term) {
        MultiSearchRequest request = new MultiSearchRequest();
        request.add(searchRequest(multimatchFuzzyQuery(term), "movie_index"));
        request.add(searchRequest(fuzzySearchQueryBuilder(term),  "taxonomy_index"));
        ActionFuture<MultiSearchResponse> r = client.multiSearch(request);
        return getTopTen(r.actionGet(2000));
    }

    private List<SearchResult> getTopTen(MultiSearchResponse response) {
        List<SearchResult> results = new ArrayList<>();
        response.iterator().forEachRemaining(item -> {
            if (!item.isFailure()) {
                item.getResponse().getHits().iterator().forEachRemaining(hit -> {
                    results.add(new SearchResult(hit));
                });
            }
        });

        results.sort((searchResult1, searchResult2) -> (int) (searchResult1.getScore() - searchResult2.getScore()));
        return results.stream().limit(10).collect(Collectors.toList());
    }

    private SearchRequest searchRequest(QueryBuilder queryBuilder, String... indices) {
        SearchSourceBuilder search = new SearchSourceBuilder();
        search.query(queryBuilder);
        SearchRequest request = new SearchRequest();
        request.indices(indices);
        request.source(search);
        return request;
    }

    private QueryBuilder fuzzySearchQueryBuilder(String term) {
        QueryBuilder fuzzy = QueryBuilders.fuzzyQuery("name", term);
        return fuzzy;
    }

    private QueryBuilder multimatchFuzzyQuery(String term) {
        MultiMatchQueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery(term, "name", "full_name");
        multiMatchQuery.fuzziness();
        return multiMatchQuery;
    }
}
