package com.br.game_library.mapper;

import com.br.game_library.dto.game.GameRequestDTO;
import com.br.game_library.dto.game.GameRequestUpdateDTO;
import com.br.game_library.dto.game.GameResponseDTO;
import com.br.game_library.entity.Game;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Converts between Entity {@link Game} and their DTOs
 * <p>
 * Keeps the entity isolated of layer web: the controller works only with DTOs
 */

@Component
public class GameMapper {

    /**
     * Converts an Entity into DTO response
     *
     * @param game Entity to convert
     * @return DTO with data of Game, including ID
     */
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

    /**
     * Converts DTO register into a new Entity (without ID)
     *
     * @param dto Receives data in request of register
     * @return A new Entity made for persistence
     */
    public Game toEntity(GameRequestDTO dto){
        Game game = new Game();

        game.setTitle(dto.title());
        game.setGenre(dto.genre());
        game.setPlatform(dto.platform());
        game.setReleaseYear(dto.releaseYear());
        game.setRating(dto.rating());
        game.setDescription(dto.description());
        game.setFavorite(false);

        return game;
    }

    /**
     * Converts a list of Entity's into a new list of request's DTO's
     *
     * @param games Entity's to convert
     * @return A list immutable of DTO's, in the same order of entry
     */
    public List<GameResponseDTO> toResponseList(List<Game> games){
        return games.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    /**
     * Copy data of update DTO for an Entity already existing
     * <p>
     * All fields are overwritten
     *
     * @param requestUpdateDTO A new data receives in the request
     * @param game Entity what will be modified in-place
     */
    public void updateEntity(GameRequestUpdateDTO requestUpdateDTO, Game game){
        game.setTitle(requestUpdateDTO.title());
        game.setGenre(requestUpdateDTO.genre());
        game.setPlatform(requestUpdateDTO.platform());
        game.setReleaseYear(requestUpdateDTO.releaseYear());
        game.setRating(requestUpdateDTO.rating());
        game.setDescription(requestUpdateDTO.description());
    }
}