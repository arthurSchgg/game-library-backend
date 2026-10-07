package com.br.game_library.mapper;

import com.br.game_library.dto.GameRequestDTO;
import com.br.game_library.dto.GameRequestUpdateDTO;
import com.br.game_library.dto.GameResponseDTO;
import com.br.game_library.entity.Game;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameMapper {

    public GameResponseDTO toResponseDTO(Game game){
        return new GameResponseDTO(
                game.getId(),
                game.getTitle(),
                game.getGenre(),
                game.getPlatform(),
                game.getReleaseYear(),
                game.getRating(),
                game.getDescription(),
                game.getImageUrl(),
                game.getFavorite()
        );
    }

    public Game toEntity(GameRequestDTO dto){
        Game game = new Game();

        game.setTitle(dto.title());
        game.setGenre(dto.genre());
        game.setPlatform(dto.platform());
        game.setReleaseYear(dto.releaseYear());
        game.setRating(dto.rating());
        game.setDescription(dto.description());
        game.setImageUrl(dto.imageUrl());
        game.setFavorite(false);
        return game;
    }

    public List<GameResponseDTO> toResponseList(List<Game> games){
        return games.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public void updateEntity(GameRequestUpdateDTO requestUpdateDTO, Game game){
        game.setTitle(requestUpdateDTO.title());
        game.setGenre(requestUpdateDTO.genre());
        game.setPlatform(requestUpdateDTO.platform());
        game.setReleaseYear(requestUpdateDTO.releaseYear());
        game.setRating(requestUpdateDTO.rating());
        game.setDescription(requestUpdateDTO.description());
        game.setImageUrl(requestUpdateDTO.imageUrl());
    }
}
