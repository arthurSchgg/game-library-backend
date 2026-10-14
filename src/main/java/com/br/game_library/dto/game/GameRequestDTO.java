package com.br.game_library.dto.game;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Input data for registering the Game entity
 *
 * @param title       Title of the game
 * @param genre       Genre of the game
 * @param platform    Platform of the game
 * @param releaseYear ReleaseYear of the game
 * @param rating      Rating of the game
 * @param description Description of the game
 */
@Schema(description = "Input data for registering a Game")
public record GameRequestDTO(

        @Schema(
                description = "Title of the game",
                example = "Super Mario"
        )
        String title,

        @Schema(
                description = "Genre of the game",
                example = "Action"
        )
        String genre,

        @Schema(
                description = "Platform of the game",
                example = "PC"
        )
        String platform,

        @Schema(
                description = "Release year of the game",
                example = "1999"
        )
        Integer releaseYear,

        @Schema(
                description = "Rating of the game",
                example = "10"
        )
        Double rating,

        @Schema(
                description = "Description   of the game",
                example = "The Super Mario game is a classic game from Nintendo"
        )
        String description

) {
}
