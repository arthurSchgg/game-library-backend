package com.br.game_library.dto.steam;

public record SteamGameDTO (
        Long steam_appid,
        String name,
        String short_description,
        String header_image
){}
