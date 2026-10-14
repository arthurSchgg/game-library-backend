package com.br.game_library.dto.game;

import io.swagger.v3.oas.annotations.media.Schema;

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
        String title,

        @Schema(
                description = "A new genre for the game",
                example = "Strategy"
        )
        String genre,

        @Schema(
                description = "A new platform for the game",
                example = "Smartphone"
        )
        String platform,

        @Schema(
                description = "A new release year for the game",
                example = "1900"
        )
        Integer releaseYear,

        @Schema(
                description = "A new rating for the game",
                example = "9.5"
        )
        Double rating,

        @Schema(
                description = "A new description for the game",
                example = "The most famous strategy game in the world"
        )
        String description

) {
}
