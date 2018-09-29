package uz.oltinolma.producer.security.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
public class DBConfiguration {

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

    @Bean(name = "h2Datasource")
    public BasicDataSource h2DataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("inh2.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("inh2.datasource.url"));
        dataSource.setUsername(env.getProperty("inh2.datasource.username"));
        dataSource.setPassword(env.getProperty("inh2.datasource.password"));

        Resource initData = new ClassPathResource("scripts/h2.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initData);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);

        return dataSource;
    }

    @Primary
    @Bean
    public DataSourceTransactionManager tx(@Qualifier("datasource")BasicDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public DataSourceTransactionManager h2tx(@Qualifier("h2Datasource") BasicDataSource h2Datasource) {
        return new DataSourceTransactionManager(h2Datasource);
    }


}
