package com.br.game_library.controller.game;

import com.br.game_library.dto.game.GameRequestDTO;
import com.br.game_library.dto.game.GameRequestUpdateDTO;
import com.br.game_library.dto.game.GameResponseDTO;
import com.br.game_library.service.game.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Games", description = "Manage the games of the library: register, search, update, favorite and remove")
@Controller
@RequestMapping("v1/game-library")
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @Operation(
            summary = "List Games",
            description = "Returns all registered Games. The list is empty when there are none."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List successfully returned (empty if there are no games)",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = GameResponseDTO.class)))
            )
    })
    @GetMapping
    public ResponseEntity<List<GameResponseDTO>> listAll() {
        return ResponseEntity.ok(service.list());
    }

    @Operation(
            summary = "Register a Game",
            description = "Registers a new Game in the database. The cover image (imageUrl) and the Steam app ID "
                    + "are optional and usually come from the Steam search. "
                    + "The new Game starts as not favorite."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Game successfully created",
                    headers = @Header(name = "Location", description = "URI of the created Game"),
                    content = @Content(schema = @Schema(implementation = GameResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data (e.g. empty title or rating out of range)",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<GameResponseDTO> registerGame(@Valid @RequestBody GameRequestDTO requestDTO) {
        GameResponseDTO responseDTO = service.registerGame(requestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.id())
                .toUri();


        return ResponseEntity.created(uri).body(responseDTO);
    }

    @Operation(
            summary = "Find a Game by ID",
            description = "Returns the Game with the given ID"
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
                    description = "Game not found",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<GameResponseDTO> findById(
            @Parameter(description = "ID of the Game", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(service.findId(id));
    }

    @Operation(
            summary = "Find Games by title",
            description = "Returns the Games whose title contains the given text, ignoring upper/lower case. "
                    + "The list is empty when nothing matches."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Search done (empty list if no Game matches)",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = GameResponseDTO.class)))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "The title parameter was not informed",
                    content = @Content
            )
    })
    @GetMapping("/search")
    public ResponseEntity<List<GameResponseDTO>> findByTitle(
            @Parameter(description = "Text contained in the title (not case sensitive)", example = "zelda")
            @RequestParam String title) {
        return ResponseEntity.ok(service.findByTitle(title));
    }

    @Operation(
            summary = "Update a Game",
            description = "Replaces the data of an existing Game. All fields are overwritten, "
                    + "so send the current values of the fields that must not change. "
                    + "The favorite status is not changed by this endpoint."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Game updated",
                    content = @Content(schema = @Schema(implementation = GameResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Game not found",
                    content = @Content
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<GameResponseDTO> update(
            @Parameter(description = "ID of the Game to update", example = "1") @PathVariable Long id,
            @Valid @RequestBody GameRequestUpdateDTO requestUpdateDTO) {
        return ResponseEntity.ok(service.update(id, requestUpdateDTO));
    }

    @Operation(
            summary = "Favorite or unfavorite a Game",
            description = "Inverts the favorite status of the Game: a favorite Game becomes "
                    + "not favorite and vice versa."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Favorite status changed",
                    content = @Content(schema = @Schema(implementation = GameResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Game not found",
                    content = @Content
            )
    })
    @PatchMapping("/{id}/favorite")
    public ResponseEntity<GameResponseDTO> toggleGameFavorite(
            @Parameter(description = "ID of the Game", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(service.toggleGame(id));
    }

    @Operation(
            summary = "Remove a Game",
            description = "Permanently removes the Game with the given ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Game removed successfully",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Game not found",
                    content = @Content
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(
            @Parameter(description = "ID of the Game to remove", example = "1") @PathVariable Long id) {
        service.remove(id);

        return ResponseEntity.noContent().build();
    }
}