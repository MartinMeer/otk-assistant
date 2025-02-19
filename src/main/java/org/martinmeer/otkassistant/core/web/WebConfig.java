package org.martinmeer.otkassistant.core.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Primary
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:40155")
                //.allowedOrigins("https://otk-help.martinmeer.com")
                .allowedMethods("POST", "GET", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(3600)
                .allowCredentials(true);
    }
}

