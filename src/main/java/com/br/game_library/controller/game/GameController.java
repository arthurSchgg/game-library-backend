package com.br.game_library.controller.game;

import com.br.game_library.dto.game.GameRequestDTO;
import com.br.game_library.dto.game.GameRequestUpdateDTO;
import com.br.game_library.dto.game.GameResponseDTO;
import com.br.game_library.service.game.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jdk.jfr.ContentType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * REST controller responsible for Game endpoints
 */
@Controller
@RequestMapping("v1/game-library")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GameController {

    private final GameService service;

    /**
     * Made a Controller yours dependencies
     *
     * @param service Service with the business rules of Game
     */
    public GameController(GameService service) {
        this.service = service;
    }

    /**
     * Lists all registered Games
     *
     * @return {@code 200 OK} with list of Games (empty if there are none)
     */

    @Operation(
            summary = "List Games",
            description = "Returned all registered Games"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = " List successfully returned",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = GameResponseDTO.class)))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No games found"
            )
    })
    @GetMapping("/list")
    public ResponseEntity<List<GameResponseDTO>> listAll() {
        return ResponseEntity.ok(service.list());
    }

    /**
     * Register a Game
     *
     * @param requestDTO Game data to created
     * @return {@code 201 Created} with the saved Game and header {@code Location}
     * pointing to the new feature
     */

    @Operation(
            summary = "Register a Game",
            description = "Registers a new Game into a BD"
    )

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Game sucessfully created",
                    content = @Content(schema = @Schema(implementation = GameResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Data request invalid",
                    content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<GameResponseDTO> registerGame(@Valid @RequestBody GameRequestDTO requestDTO) {
        GameResponseDTO responseDTO = service.registerGame(requestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(responseDTO.id())
                .toUri();


        return ResponseEntity.created(uri).body(responseDTO);
    }

    /**
     * Find a Game by ID
     *
     * @param id Identifier of the Game
     * @return {@code 200 OK} with the Game found
     */

    @Operation(
            summary = "Find a Game by ID",
            description = "Search for a game by ID"
    )

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Game found",
                    content = @Content(
                            schema = @Schema(implementation = GameResponseDTO.class))
            ),
            @ApiResponse (
                    responseCode = "404",
                    description = "Game not found"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<GameResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findId(id));
    }

    /**
     * Find a Game by Title
     *
     * @param title Title of the game
     * @return {@code 200 OK} with the Game found
     */

    @Operation(
            summary = "Find a Game by title",
            description = "Search for a game by title"
    )

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Game found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = GameResponseDTO.class)))
            ),
            @ApiResponse (
                    responseCode = "404",
                    description = "Game not found"
            )
    })
    @GetMapping("/search")
    public ResponseEntity<List<GameResponseDTO>> findByTitle(@RequestParam String title) {
        return ResponseEntity.ok(service.findByTitle(title));
    }

    /**
     * Made an update of a Game
     *
     * @param id Identifier of the Game
     * @param requestUpdateDTO New game data
     * @return {@code 200 OK} with update Game
     */

    @Operation(
            summary = "Update a Game",
            description = "Made an update of a Game"
    )

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Updated game",
                    content = @Content(schema = @Schema(implementation = GameResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Game not found",
                    content = @Content
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<GameResponseDTO> update(@PathVariable Long id, @Valid @RequestBody GameRequestUpdateDTO requestUpdateDTO) {
        return ResponseEntity.ok(service.update(id, requestUpdateDTO));
    }

    /**
     * Made a favorite or unfavorite a Game
     *
     * @param id Identifier of the Game
     * @return {@code 200 OK} with favorite or unfavorite Game
     */

    @Operation(
            summary = "Made a favorite or unfavorite a Game"
    )

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Game favorited or unfavorited",
                    content = @Content(schema = @Schema(implementation = GameResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Game not found",
                    content = @Content
            )
    })
    @PatchMapping("/{id}/favorite")
    public ResponseEntity<GameResponseDTO> toggleGameFavorite(@PathVariable Long id) {
        return ResponseEntity.ok(service.toggleGame(id));
    }

    /**
     * Removes a Game
     *
     * @param id Identifier of the Game
     * @return {@code 204 No Content} when the removal is complete
     */

    @Operation(
            summary = "Removes a Game"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Removed successfully game",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Game not found",
                    content = @Content
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        service.remove(id);

        return ResponseEntity.noContent().build();
    }
}
