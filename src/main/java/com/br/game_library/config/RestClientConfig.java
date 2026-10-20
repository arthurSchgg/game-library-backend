package com.br.game_library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Configuration of the HTTP client used to consume external APIs (Steam Store).
 */
@Configuration
public class RestClientConfig {

    /**
     * Exposes a {@link RestClient.Builder} so services can create their own
     * {@link RestClient} with a specific base URL.
     *
     * @return A new builder of {@link RestClient}
     */
    @Bean
    public RestClient.Builder restClientBuilder(){
        return RestClient.builder();
    }
}