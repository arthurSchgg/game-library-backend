package com.br.game_library.repository;

import com.br.game_library.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    GameReponseDTO findByTitleContainingIgnoreCase(String title);

}
