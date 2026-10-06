package com.br.game_library.dto.steam;

public record SteamResponseDTO (
        Long appId,

        String name,

        String description,

        String imageUrl,

        String releaseDate,

        String developer,

        String publisher,

        Double price
){}
