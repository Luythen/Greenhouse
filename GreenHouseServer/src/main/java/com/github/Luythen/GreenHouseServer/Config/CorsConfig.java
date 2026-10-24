package com.github.Luythen.GreenHouseServer.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration 
public class CorsConfig {

    @Value("${allowed_origin}")
    private String origin;

    @Bean
    public WebMvcConfigurer configurer () {
        return new WebMvcConfigurer() {
            @Override 
            public void addCorsMappings (CorsRegistry cors) {
                cors.addMapping("/**")
                    .allowCredentials(true)
                    .allowedHeaders("*")
                    .allowedOrigins(origin)
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS");      
            }
        };
        
    }

}
