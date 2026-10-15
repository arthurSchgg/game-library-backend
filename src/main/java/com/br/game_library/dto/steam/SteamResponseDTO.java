package com.br.game_library.dto.steam;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Wrapper of the Steam Store API ({@code appdetails}) for a single game.
 *
 * @param success Whether Steam found the app ID
 * @param data    Details of the game (the useful content is here)
 */
@Schema(description = "Steam response for a single game")
public record SteamResponseDTO (
        @Schema(description = "Whether Steam found the app ID", example = "true")
        Boolean success,

        @Schema(description = "Details of the game")
        SteamGameDTO data
){}