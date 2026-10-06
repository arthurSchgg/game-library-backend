package com.br.game_library.dto.steam;

public record SteamResponseDTO (
        Long appId,

        String name,

        Boolean success
){}
