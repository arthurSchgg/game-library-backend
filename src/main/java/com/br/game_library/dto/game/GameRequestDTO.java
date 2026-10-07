package com.br.game_library.dto.game;

public record GameRequestDTO (

    String title,

    String genre,

    String platform,

    Integer releaseYear,

    Double rating,

    String description,

    String imageUrl

){}
