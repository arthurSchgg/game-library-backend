package com.br.game_library.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Global web configuration.
 * <p>
 * Defines the CORS rules that allow the front-end to call this API from the browser.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Allows cross-origin requests to every route of the API.
     * <p>
     * It is accessible to people from all backgrounds
     * are accepted. Browsers treat each host/port as a different origin, so both are listed.
     * {@code PATCH} and {@code OPTIONS} must be explicit: the first is used to favorite a
     * game and the second is the browser's preflight request.
     *
     * @param registry Registry where the CORS rules are added
     */
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }

}