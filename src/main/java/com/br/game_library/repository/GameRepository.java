package com.br.game_library.repository;

import com.br.game_library.dto.GameResponseDTO;
import com.br.game_library.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GameRepository extends JpaRepository<Game, Long> {

    GameResponseDTO findByTitleContainingIgnoreCase(String title);

}
