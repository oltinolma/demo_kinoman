package uz.oltinolma.producer.elasticsearch.index.tools;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IndexingTools {
    private String indexName;
    private String analyzerName;
    private String type;
    private List<String> fields;
    private Client client;

    private IndexingTools() {}

    public void deleteIndexIfExists() {
        DeleteIndexRequest dr = new DeleteIndexRequest();
        dr.indices(indexName);
        ActionFuture<DeleteIndexResponse> res = client.admin().indices().delete(dr);
        res.actionGet(2000);
    }

    public void createIndexWithEdge_ngramAnalyzer() throws IOException {
        CreateIndexRequest cir = new CreateIndexRequest();
        cir.index(indexName);
        cir.source(source());
        ActionFuture<CreateIndexResponse> res = client.admin().indices().create(cir);
        res.actionGet(5000);
    }

    private XContentBuilder source() throws IOException {
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

    public static class Builder {
        private String indexName;
        private String analyzerName;
        private String type;
        private List<String> fields = new ArrayList<>();
        private Client client;

        public Builder(Client client) {
            this.client = client;
        }

        public IndexingTools build() {
            validateFields();
            IndexingTools it = new IndexingTools();
            it.client = this.client;
            it.indexName = this.indexName;
            it.fields = this.fields;
            it.analyzerName = this.analyzerName;
            it.type = this.type;

            return it;
        }

        private void validateFields() {
            Assert.noNullElements(new Object[]{client, indexName, analyzerName, type}, "IndexingTools has null value!");
            Assert.notEmpty(fields, "Index fields cannot be empty.");
        }

        public IndexingTools.Builder addField(String field) {
            if (field != null && !field.isEmpty())
                this.fields.add(field);
            return this;
        }

        public Builder setIndexName(String indexName) {
            this.indexName = indexName;
            return this;
        }

        public Builder setAnalyzerName(String analyzerName) {
            this.analyzerName = analyzerName;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }
    }
}

