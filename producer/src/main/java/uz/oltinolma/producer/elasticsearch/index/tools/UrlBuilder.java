package uz.oltinolma.producer.elasticsearch.index.tools;

import java.net.URI;
import java.net.URISyntaxException;

public class UrlBuilder {
    private String protocol = "http://";
    private String host = System.getenv("kinoman.host");
    int port = 9200;

    public UrlBuilder(String protocol, String host, int port) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
    }

    public UrlBuilder() {}

    public URI urlForIndex(String indexName) throws URISyntaxException {
        String path = "/" + indexName;
        return new URI(protocol + host + ":" + port + path);
    }
}
