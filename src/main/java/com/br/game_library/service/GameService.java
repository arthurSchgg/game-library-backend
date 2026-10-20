package com.br.game_library.service;

import com.br.game_library.dto.GameRequestDTO;
import com.br.game_library.dto.GameRequestUpdateDTO;
import com.br.game_library.dto.GameResponseDTO;
import com.br.game_library.entity.Game;
import com.br.game_library.mapper.GameMapper;
import com.br.game_library.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer with the business rules of the Game
 */

@Service
public class GameService {

    private final GameRepository repository;
    private final GameMapper mapper;

    /**
     * Made a service with yours dependencies
     *
     * @param repository Persistence repository of Game
     * @param mapper Converter between Entity and DTO's
     */
    public GameService(GameRepository repository, GameMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Made and persist a new Game
     *
     * @param requestDTO Game data to register
     * @return Save Game DTO, with ID already created
     */
    public GameResponseDTO registerGame(GameRequestDTO requestDTO){
        Game game = mapper.toEntity(requestDTO);
        Game salvo = repository.save(game);
        return mapper.toResponseDTO(salvo);
    }

    /**
     * Lists all registered Games
     *
     * @return A list of DTO's (empty if there are no records)
     */
    public List<GameResponseDTO> list(){
        List<Game> games = repository.findAll();
        return mapper.toResponseList(games);
    }

    /**
     * Search for a game by ID
     *
     * @param id Identifier of the Game
     * @return Game DTO found
     * @throws RuntimeException If no Game with the specified ID exists
     */
    public GameResponseDTO findId(Long id){
        Game game = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with ID: " + id));

        return mapper.toResponseDTO(game);
    }

    /**
     * Search for a game by title
     *
     * @param title Title of the Game
     * @return Game DTO found
     */
    public List<GameResponseDTO> findByTitle(String title){
        List<Game> games = repository.findByTitleContainingIgnoreCase(title);
        return mapper.toResponseList(games);
    }

    /**
     * Updates the data for an existing Game
     *
     * @param id Identifier of the Game
     * @param requestUpdateDTO New Game data
     * @return Updated Game DTO
     * @throws RuntimeException If no Game with the specified ID exists
     */
    public GameResponseDTO update(Long id, GameRequestUpdateDTO requestUpdateDTO){
        Game game = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with ID: " + id));

        mapper.updateEntity(requestUpdateDTO, game);

        Game updateGame = repository.save(game);

        return mapper.toResponseDTO(updateGame);
    }

    /**
     * Favorite and unfavorite an existing game
     *
     * @param id Identifier of the Game
     * @return The Favorite attribute of the Game entity changed to True or False
     * @throws RuntimeException If no Game with the specified ID exists
     */
    public GameResponseDTO toggleGame(Long id){
        Game game = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with ID: " + id));

        game.setFavorite(!Boolean.TRUE.equals(game.getFavorite()));

        Game updateGame = repository.save(game);

        return mapper.toResponseDTO(updateGame);
    }

    /**
     * Deletes the game selected by ID
     *
     * @param id Identifier of the Game
     * @throws RuntimeException If no Game with the specified ID exists
     */
    public void remove(Long id){
        Game game = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with ID: " + id));

        repository.delete(game);
    }
}
