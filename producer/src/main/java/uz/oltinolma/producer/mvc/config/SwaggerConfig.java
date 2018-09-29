package uz.oltinolma.producer.mvc.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("uz.oltinolma.producer"))
                .paths(PathSelectors.ant("/*"))
                .build()
                .apiInfo(apiInfo());
    }

    @Bean
    ApiInfo apiInfo() {
        return new ApiInfo(
                "Demo Kinoman API",
                "API created by java tarvuz team.",
                "v.0.0.1",
                "Terms of service",
                new Contact("Azam aka", "www.oltinolma.uz", "azamshaazam@gmail.com"),
                "API doesn't have license", "xx", Collections.emptyList());
    }
}
