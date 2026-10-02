package com.br.game_library.repository;

import com.br.game_library.dto.GameResponseDTO;
import com.br.game_library.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface GameRepository extends JpaRepository<Game, Long> {

    List<GameResponseDTO> findByTitleContainingIgnoreCase(String title);

}
