package com.br.game_library.dto.steam;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * One result of the Steam Store search.
 *
 * @param id        Steam app ID of the game (use it to find the full details)
 * @param name      Name of the game
 * @param tinyImage URL of a small image of the game (JSON field {@code tiny_image})
 */
@Schema(description = "One game found in the Steam Store search")
public record SteamSearchItemDTO(

        @Schema(description = "Steam app ID of the game", example = "1245620")
        Long id,

        @Schema(description = "Name of the game", example = "ELDEN RING")
        String name,

        @Schema(description = "URL of a small image of the game")
        @JsonProperty("tiny_image") String tinyImage
) {}