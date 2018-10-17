package uz.oltinolma.producer.elasticsearch.index.tools;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Setting;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.analysis.Analysis;
import org.elasticsearch.index.mapper.Mapping;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class IndexingTools {
    private RestTemplate restTemplate;
    private String indexName;
    private String analyzerName;
    private String type;
    private List<String> fields = new ArrayList<>();
    private UrlBuilder urlBuilder = new UrlBuilder();

    public IndexingTools(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void deleteIndexIfExists() throws URISyntaxException {
        restTemplate.delete(urlBuilder.urlForIndex(indexName));
    }

    private void validateFields() {
        Assert.noNullElements(new Object[]{restTemplate, indexName, analyzerName, type}, "IndexingTools has null value!");
        Assert.notEmpty(fields, "Index fields cannot be empty.");
    }


    public IndexingTools setIndexName(String indexName) {
        this.indexName = indexName;
        return this;
    }

    public IndexingTools setAnalyzerName(String analyzerName) {
        this.analyzerName = analyzerName;
        return this;
    }


    public IndexingTools setType(String type) {
        this.type = type;
        return this;
    }


    public IndexingTools addField(String field) {
        if (field != null && !field.isEmpty())
            this.fields.add(field);
        return this;
    }

    public String indexWithEdge_ngramFilter(Client client) throws IOException {
        validateFields();
        CreateIndexRequest cir = new CreateIndexRequest();
        cir.index(indexName);
        cir.source(source());
        ActionFuture<CreateIndexResponse> res = client.admin().indices().create(cir);
        CreateIndexResponse response = res.actionGet(5000);
        System.out.println("INDEX : " + response.index());
        System.out.println("INDEX isShardsAcked : " + response.isShardsAcked());
        return source().string();

    }


    public XContentBuilder source() throws IOException {
        String filterName = analyzerName + "_filter";
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("settings");
            {
                builder.startObject("analysis");
                {
                    builder.startObject("filter");
                    {
                        builder.startObject(filterName);
                        {
                            builder.field("type", "edge_ngram");
                            builder.field("min_gram", 1);
                            builder.field("max_gram", 20);
                        }
                        builder.endObject();
                    }
                    builder.endObject();
                    builder.startObject("analyzer");
                    {
                        builder.startObject(analyzerName);
                        {
                            builder.field("type", "custom");
                            builder.field("tokenizer", "standard");
                            builder.array("filter", "lowercase", filterName);
                        }
                        builder.endObject();
                    }
                    builder.endObject();
                }
                builder.endObject();
            }
            builder.endObject();
            builder.startObject("mappings");
            {
                builder.startObject(type);
                {
                    builder.startObject("properties");
                    {

                        for (String field : fields) {
                            builder.startObject(field);
                            {
                                builder.field("type", "text");
                                builder.field("analyzer", analyzerName);
                                builder.field("search_analyzer", "standard");
                            }
                            builder.endObject();
                        }

                    }
                    builder.endObject();
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();

        return builder;
    }
}

