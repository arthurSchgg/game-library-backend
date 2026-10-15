package com.br.game_library.repository;

import com.br.game_library.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository Spring Data JPA of the Entity {@link Game}
 */
public interface GameRepository extends JpaRepository<Game, Long> {

    /**
     * Finds the Games whose title contains the given text, ignoring upper/lower case
     * <p>
     * Example: {@code "zel"} finds "Zelda" and "The Legend of Zelda".
     *
     * @param title Text to search in the title of the Game
     * @return The matching Games (empty if there are none)
     */
    List<Game> findByTitleContainingIgnoreCase(String title);

}