package uz.oltinolma.producer.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("PUT","DELETE","GET","POST","OPTIONS")
                        .allowedHeaders("X-Authorization", "Content-Type", "Accept", "x-requested-with", "Cache-Control", "Routing-Key")
                        .exposedHeaders("Cache-Control", "Content-Type")
                        .allowCredentials(false);
            }
        };
    }


}
