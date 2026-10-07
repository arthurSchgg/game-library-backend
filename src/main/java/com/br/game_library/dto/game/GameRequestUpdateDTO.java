package com.br.game_library.dto.game;

public record GameRequestUpdateDTO(

        String title,

        String genre,

        String platform,

        Integer releaseYear,

        Double rating,

        String description,

        String imageUrl
){}
