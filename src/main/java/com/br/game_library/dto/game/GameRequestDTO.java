package com.br.game_library.dto.game;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

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
        @NotBlank(message = "O título é obrigatório")
        @Size(max = 150)
        String title,

        Long steamAppId,

        String imageUrl,

        @Schema(
                description = "Genre of the game",
                example = "Action"
        )
        @Size(max = 100)
        String genre,

        @Schema(
                description = "Platform of the game",
                example = "PC"
        )
        @Size(max = 50)
        String platform,

        @Schema(
                description = "Release year of the game",
                example = "1999"
        )
        @Min(1970)
        @Max(2027)
        Integer releaseYear,

        @Schema(
                description = "Rating of the game",
                example = "10"
        )
        @DecimalMin("0.0")
        @DecimalMax("10.0")
        Double rating,

        @Schema(
                description = "Description   of the game",
                example = "The Super Mario game is a classic game from Nintendo"
        )
        String description

) {
}
