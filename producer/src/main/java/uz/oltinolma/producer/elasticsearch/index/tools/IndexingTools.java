package uz.oltinolma.producer.elasticsearch.index.tools;

import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

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

    public void createIndex() throws URISyntaxException {
        validateFields();
        //sorry for toArray. It is the most stupid API from Oracle
        restTemplate.put(urlBuilder.urlForIndex(indexName), indexWithEdge_ngramFilter(fields.toArray(new String[0])));
        System.out.println(indexWithEdge_ngramFilter(fields.toArray(new String[0])));
    }

    public void deleteIndexIfExists() throws URISyntaxException {
        restTemplate.delete(urlBuilder.urlForIndex(indexName));
    }

    private void validateFields() {
        Assert.noNullElements(new Object[]{restTemplate, indexName, analyzerName, type}, "IndexingTools has null value!");
        Assert.notEmpty(fields, "Index fields cannot be empty.");
    }

    private String indexWithEdge_ngramFilter(String... fields) {
        String filterName = analyzerName + "_filter";
        String json = "{\n" +
                "  \"settings\": {\n" +
                "    \"analysis\": {\n" +
                "      \"filter\": {\n" +
                "        \"" + filterName + "\": {\n" +
                "          \"type\": \"edge_ngram\",\n" +
                "          \"min_gram\": 1,\n" +
                "          \"max_gram\": 20\n" +
                "        }\n" +
                "      },\n" +
                "      \"analyzer\": {\n" +
                "        \"" + analyzerName + "\": { \n" +
                "          \"type\": \"custom\",\n" +
                "          \"tokenizer\": \"standard\",\n" +
                "          \"filter\": [\n" +
                "            \"lowercase\",\n" +
                "            \"" + filterName + "\"\n" +
                "          ]\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"mappings\": {\n" +
                "    \"" + type + "\": {\n" +
                "      \"properties\": {\n" +
                         properties(fields) +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
        return json;
    }


    private String textProperty(String name) {
        String json = " \"" + name + "\": {\n" +
                "          \"type\": \"text\",\n" +
                "          \"analyzer\": \"" + analyzerName + "\", \n" +
                "          \"search_analyzer\": \"standard\" \n" +
                "        }";
        return json;
    }

    private String properties(String... fields) {
        StringJoiner joiner = new StringJoiner(",");
        for (String field : fields)
            joiner.add(textProperty(field));
        return joiner.toString();
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
}
