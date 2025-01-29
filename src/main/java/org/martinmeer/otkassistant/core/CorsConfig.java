package org.martinmeer.otkassistant.core;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**") // Разрешить все пути
                    .allowedOrigins("http://otk-help.martinmeer.com") // Разрешенные домены
                    .allowedMethods("GET", "POST") // Разрешенные методы
                    .allowedHeaders("*") // Разрешенные заголовки
                    .allowCredentials(true); // Разрешить куки и авторизацию
        }

}
