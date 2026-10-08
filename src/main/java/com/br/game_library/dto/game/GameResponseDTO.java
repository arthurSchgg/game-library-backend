package com.br.game_library.dto.game;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Output data of a game, returned by the API
 *
 * @param id          Identifier generated for the BD
 * @param title       Title of the game
 * @param genre       Genre of the game
 * @param platform    Platform of the game
 * @param releaseYear Release year of the game
 * @param rating      Rating of the game
 * @param description Description of the game
 * @param imageUrl    Image URL of the game
 * @param favorite    Game added to favorites
 */
@Schema(description = "Game returned by the API")
public record GameResponseDTO(

        @Schema(
                description = "Identifier of the Game",
                example = "10"
        )
        Long id,

        @Schema(
                description = "Title of the game",
                example = "Fortnite"
        )
        String title,

        @Schema(
                description = "Genre of the game",
                example = "Multiplayer"
        )
        String genre,

        @Schema(
                description = "Platform of the game",
                example = "PC and consoles"
        )
        String platform,

        @Schema(
                description = "Release year of the game",
                example = "2016"
        )
        Integer releaseYear,

        @Schema(
                description = "Rating of the game",
                example = "8.5"
        )
        Double rating,

        @Schema(
                description = "Description of the game",
                example = "A most famous multiplayer game of the world"
        )
        String description,

        @Schema(
                description = "Image url of the game",
                example = "https://cdn2.unrealengine.com/fortnite-og-image-1200x630-1200x630-6f2e5c5f8c5a.jpg"
        )
        String imageUrl,

        @Schema(
                description = "Favorite or unfavorite the game",
                example = "True or false"
        )
        Boolean favorite

) {
}
