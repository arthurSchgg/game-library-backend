package com.br.game_library.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

/**
 * Input data for updating a Game
 *
 * @param title       New name
 * @param genre       New genre
 * @param platform    New platform
 * @param releaseYear New release year
 * @param rating      New rating
 * @param description New description
 */
@Schema(description = "Input data for updating a Game")
public record GameRequestUpdateDTO(

        @Schema(
                description = "A new title for the game",
                example = "Xadrez"
        )
        @NotBlank(message = "O título é obrigatório")
        @Size(max = 150)
        String title,

        @Schema(
                description = "Game cover",
                example = "https://cdn.akamai.steamstatic.com/steam/apps/1091500/header.jpg"
        )
        String imageUrl,

        @Schema(
                description = "A new genre for the game",
                example = "Strategy"
        )
        @Size(max = 100)
        String genre,

        @Schema(
                description = "A new platform for the game",
                example = "Smartphone"
        )
        @Size(max = 50)
        String platform,

        @Schema(
                description = "A new release year for the game",
                example = "1900"
        )
        @Min(1970)
        @Max(2027)
        Integer releaseYear,

        @Schema(
                description = "A new rating for the game",
                example = "9.5"
        )
        @DecimalMin("0.0")
        @DecimalMax("10.0")
        Double rating,

        @Schema(
                description = "A new description for the game",
                example = "The most famous strategy game in the world"
        )
        String description

) {
}
