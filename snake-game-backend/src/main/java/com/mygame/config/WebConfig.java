package com.mygame.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class WebConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*"); // Autoriser toutes les origines
        config.addAllowedMethod("*"); // Autoriser toutes les méthodes (GET, POST, PUT, DELETE)
        config.addAllowedHeader("*"); // Autoriser tous les headers
        config.setAllowCredentials(true); // Autoriser les cookies (si besoin)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
