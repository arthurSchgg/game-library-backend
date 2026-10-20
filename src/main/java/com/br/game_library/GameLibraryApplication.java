package com.br.game_library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the Game Library API.
 * <p>
 * Starts the Spring Boot application (REST API on port 8085, MySQL persistence
 * and integration with the Steam Store API).
 */
@SpringBootApplication
public class GameLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameLibraryApplication.class, args);
    }

}