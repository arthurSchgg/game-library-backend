package com.br.game_library.dto;

public record GameResponseDTO (

        Long id,

        String title,

        String genre,

        String platform,

        Integer releaseYear,

        Double rating,

        String description,

        String imageUrl,

        Boolean favorite

){}
