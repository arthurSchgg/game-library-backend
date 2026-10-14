package com.br.game_library.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        Server localServer = new Server()
                .url("http://localhost:8085")
                .description("Development Server (Local)");

        return new OpenAPI()
                .info(new Info()
                        .title("Game Library API")
                        .version("1.0.0")
                        .description("Game library API, consuming the Steam API.")
                        .contact(new Contact()
                                .name("Arthur Miguel Schlichting")
                                .email("arthurms2904@gmail.com")
                                .url("/v1/game-library"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")))
                .servers(List.of(localServer));
    }
}
