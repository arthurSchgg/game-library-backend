package com.br.game_library.dto.steam;

public record SteamResponseDTO (
        Boolean success,
        SteamGameDTO data
){}
