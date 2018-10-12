package uz.oltinolma.producer.config;

import org.elasticsearch.client.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.client.NodeClientFactoryBean;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import uz.oltinolma.producer.elasticsearch.config.ESConfig;

import java.io.File;

@Configuration
@Import(ESConfig.class) // the actual configuration
@Profile("test-elastic-profile")
public class TestConfig {
    @Bean
    public Client client() throws Exception {
        File tmpDir = File.createTempFile("elastic", Long.toString(System.nanoTime()));
        NodeClientFactoryBean temp = new NodeClientFactoryBean(true);
        temp.setPathData(new File(tmpDir, "data").getAbsolutePath());
        temp.setPathHome(tmpDir.getAbsolutePath());
        temp.setClusterName("test");
        return temp.getObject();
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws Exception {
        return new ElasticsearchTemplate(client());
    }
}