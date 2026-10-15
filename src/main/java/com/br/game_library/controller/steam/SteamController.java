package com.br.game_library.controller.steam;

import com.br.game_library.dto.steam.SteamResponseDTO;
import com.br.game_library.dto.steam.SteamSearchResponseDTO;
import com.br.game_library.service.steam.SteamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Steam", description = "Queries the Steam Store to get the app ID, title and cover image of a game")
@Controller
@RequestMapping("v1/steam")
public class SteamController {

    private final SteamService service;

    public SteamController(SteamService service) {
        this.service = service;
    }

    @Operation(
            summary = "Find a Steam game by app ID",
            description = "Returns the details of one game of the Steam Store (name, short description "
                    + "and cover image). The useful content is inside the data field. "
                    + "Use the id returned by the Steam search."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Steam response returned",
                    content = @Content(schema = @Schema(implementation = SteamResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No game found on Steam with this app ID",
                    content = @Content
            )
    })
    @GetMapping("/{appId}")
    public ResponseEntity<SteamResponseDTO> findGame(
            @Parameter(description = "Steam app ID of the game", example = "1245620") @PathVariable Long appId){
        return ResponseEntity.ok(service.findGame(appId));
    }

    @Operation(
            summary = "Search Steam games by name",
            description = "Returns the games of the Steam Store that match the given name, "
                    + "each one with its app ID and a small image. The list is empty when nothing matches."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Search done (items is empty if nothing matches)",
                    content = @Content(schema = @Schema(implementation = SteamSearchResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "The name parameter was not informed",
                    content = @Content
            )
    })
    @GetMapping("/search")
    public ResponseEntity<SteamSearchResponseDTO> searchByName(
            @Parameter(description = "Name (or part of the name) of the game", example = "elden")
            @RequestParam String name){
        return ResponseEntity.ok(service.searchByName(name));
    }
}