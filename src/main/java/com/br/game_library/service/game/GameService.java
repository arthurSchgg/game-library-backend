package com.br.game_library.service.game;

import com.br.game_library.dto.game.GameRequestDTO;
import com.br.game_library.dto.game.GameRequestUpdateDTO;
import com.br.game_library.dto.game.GameResponseDTO;
import com.br.game_library.entity.Game;
import com.br.game_library.mapper.GameMapper;
import com.br.game_library.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private final GameRepository repository;
    private final GameMapper mapper;

    public GameService(GameRepository repository, GameMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public GameResponseDTO registerGame(GameRequestDTO requestDTO){
        Game game = mapper.toEntity(requestDTO);
        Game salvo = repository.save(game);

        return mapper.toResponseDTO(salvo);
    }

    public List<GameResponseDTO> list(){
        List<Game> games = repository.findAll();
        return mapper.toResponseList(games);
    }

    public GameResponseDTO findId(Long id){
        Game game = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with ID: " + id));

        return mapper.toResponseDTO(game);
    }

    public List<GameResponseDTO> findByTitle(String title){
        return repository.findByTitleContainingIgnoreCase(title);
    }

    public GameResponseDTO update(Long id, GameRequestUpdateDTO requestUpdateDTO){
        Game game = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with ID: " + id));

        mapper.updateEntity(requestUpdateDTO, game);

        Game updateGame = repository.save(game);

        return mapper.toResponseDTO(updateGame);
    }

    public void remove(Long id){
        Game game = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with ID: " + id));

        repository.delete(game);
    }
}
