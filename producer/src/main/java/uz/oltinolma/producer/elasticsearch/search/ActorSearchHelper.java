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
public class ActorSearchHelper extends AbstractSearchHelper {

    public List<SearchResult> searchForActors(String term) {
        List<SearchResult> actors = actorsNgramSearch(term);

        if (actors.isEmpty()) {
            actors = actorsFuzzySearch(ifMultipleWordsChooseLongestOne(term));
        }
        return actors;
    }

    private List<SearchResult> actorsFuzzySearch(String term) {
        QueryBuilder fuzzy = QueryBuilders.boolQuery()
                .filter(QueryBuilders.matchQuery("taxonomy", "actors"))
                .must(QueryBuilders.fuzzyQuery("name", term));

        SearchRequest request = searchRequest(fuzzy, "taxonomy_index");
        ActionFuture<SearchResponse> r = client.search(request);
        r = client.search(request);
        return getTopTen(r.actionGet(2000));

    }

    private List<SearchResult> actorsNgramSearch(String term) {
        QueryBuilder ngram = QueryBuilders.boolQuery()
                .filter(QueryBuilders.matchQuery("taxonomy", "actors"))
                .must(QueryBuilders.matchQuery("name", term));

        SearchRequest request = searchRequest(ngram, "taxonomy_index");
        ActionFuture<SearchResponse> r = client.search(request);
        r = client.search(request);
        return getTopTen(r.actionGet(2000));
    }
}
