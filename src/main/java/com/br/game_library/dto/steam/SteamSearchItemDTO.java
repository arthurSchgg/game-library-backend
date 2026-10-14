package com.br.game_library.dto.steam;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SteamSearchItemDTO(

        Long id,
        String name,
        @JsonProperty("tiny_image") String tinyImage
) {}
