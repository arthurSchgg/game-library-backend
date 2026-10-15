package com.br.game_library.dto.steam;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Result of a search in the Steam Store.
 *
 * @param total Number of games found
 * @param items Games found (empty if there are none)
 */
@Schema(description = "Result of a search in the Steam Store")
public record SteamSearchResponseDTO(

        @Schema(description = "Number of games found", example = "3")
        Integer total,

        @Schema(description = "Games found")
        List<SteamSearchItemDTO> items
) {}