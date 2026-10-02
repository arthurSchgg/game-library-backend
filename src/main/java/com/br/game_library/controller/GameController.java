package com.br.game_library.controller;

import com.br.game_library.dto.GameRequestDTO;
import com.br.game_library.dto.GameRequestUpdateDTO;
import com.br.game_library.dto.GameResponseDTO;
import com.br.game_library.service.GameService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("v1/game-library")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public ResponseEntity<List<GameResponseDTO>> listAll(){
        return ResponseEntity.ok(service.list());
    }

    @PostMapping("/register")
    public ResponseEntity<GameResponseDTO> registerGame(@Valid @RequestBody GameRequestDTO requestDTO){
        GameResponseDTO responseDTO = service.registerGame(requestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(responseDTO.id())
                .toUri();


        return ResponseEntity.created(uri).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findId(id));
    }
    /*
    @GetMapping("/{tile}")
    public ResponseEntity<GameResponseDTO> findByTitle(@PathVariable String title){
        return ResponseEntity.ok(service.findByTitle(title));
    }

     */

    @PutMapping("/{id}")
    public ResponseEntity<GameResponseDTO> update(@PathVariable Long id, @Valid @RequestBody GameRequestUpdateDTO requestUpdateDTO){
        return ResponseEntity.ok(service.update(id, requestUpdateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id){
        service.remover(id);

        return ResponseEntity.noContent().build();
    }
}
