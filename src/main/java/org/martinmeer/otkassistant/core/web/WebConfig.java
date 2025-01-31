package org.martinmeer.otkassistant.core.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:40155/")
                .allowedOrigins("http://localhost:8080")
                .allowedOrigins("http://otk-help.martinmeer.com")
                .allowedMethods("POST", "GET")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}

