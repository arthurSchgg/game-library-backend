package com.br.game_library.repository;

import com.br.game_library.dto.game.GameResponseDTO;
import com.br.game_library.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository Spring Data JPA of the Entity {@link Game}
 */
public interface GameRepository extends JpaRepository<Game, Long> {

    /**
     * Find the Games whose title is exactly the person informed
     *
     * @param title Title of the Game filtering
     * @return The Game the type informed
     */
    List<GameResponseDTO> findByTitleContainingIgnoreCase(String title);

}
