package com.br.game_library.dto.steam;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Game details returned by the Steam Store API ({@code appdetails}).
 * <p>
 * The field names follow the Steam JSON (snake_case) so they are read without extra mapping.
 *
 * @param steam_appid       Steam app ID of the game
 * @param name              Name of the game
 * @param short_description Short description of the game
 * @param header_image      URL of the game cover image
 */
@Schema(description = "Game details returned by the Steam Store")
public record SteamGameDTO (
        @Schema(description = "Steam app ID of the game", example = "1245620")
        Long steam_appid,

        @Schema(description = "Name of the game", example = "ELDEN RING")
        String name,

        @Schema(description = "Short description of the game")
        String short_description,

        @Schema(description = "URL of the game cover image")
        String header_image
){}