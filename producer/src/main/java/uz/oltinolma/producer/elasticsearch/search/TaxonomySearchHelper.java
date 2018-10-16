package uz.oltinolma.producer.elasticsearch.search;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxonomySearchHelper extends AbstractSearchHelper {
    public List<SearchResult> searchForTaxonomy(String term, String parent) {
        List<SearchResult> taxonomies = taxonomyNgramSearch(term, parent);

        if (taxonomies.isEmpty()) {
            taxonomies = taxonomyFuzzySearch(ifMultipleWordsChooseLongestOne(term), parent);
        }
        return taxonomies;
    }

    private List<SearchResult> taxonomyFuzzySearch(String term, String parent) {
        QueryBuilder fuzzy = QueryBuilders.boolQuery()
                .filter(QueryBuilders.matchQuery("taxonomy", parent))
                .must(QueryBuilders.fuzzyQuery("name", term));

        SearchRequest request = searchRequest(fuzzy, "taxonomy_index");
        ActionFuture<SearchResponse> r = client.search(request);
        return getTopTen(r.actionGet(2000));

    }

    private List<SearchResult> taxonomyNgramSearch(String term, String parent) {
        QueryBuilder ngram = QueryBuilders.boolQuery()
                .filter(QueryBuilders.matchQuery("taxonomy", parent))
                .must(QueryBuilders.matchQuery("name", term));

        SearchRequest request = searchRequest(ngram, "taxonomy_index");
        ActionFuture<SearchResponse> r = client.search(request);
        return getTopTen(r.actionGet(2000));
    }
}
