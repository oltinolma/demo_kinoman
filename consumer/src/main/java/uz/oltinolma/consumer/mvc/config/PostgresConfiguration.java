package uz.oltinolma.consumer.mvc.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class PostgresConfiguration {

    private Environment env;

    @Autowired
    public void setEnv(Environment env) {
        this.env = env;
    }

    @Primary
    @Bean(name = "datasource")
    public BasicDataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.dbcp2.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.dbcp2.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.dbcp2.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.dbcp2.password"));
        dataSource.setMaxActive(Integer.valueOf(env.getProperty("spring.datasource.dbcp2.max-total")));
        dataSource.setMaxIdle(Integer.valueOf(env.getProperty("spring.datasource.dbcp2.max-idle")));
        dataSource.setInitialSize(Integer.valueOf(env.getProperty("spring.datasource.dbcp2.initial-size")));
        return dataSource;
    }


    @Bean
    public DataSourceTransactionManager tx() {
        return new DataSourceTransactionManager(dataSource());
    }

}
