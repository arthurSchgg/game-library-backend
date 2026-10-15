package com.br.game_library.service.steam;

import com.br.game_library.dto.steam.SteamResponseDTO;
import com.br.game_library.dto.steam.SteamSearchResponseDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

/**
 * Service layer that consumes the Steam Store API.
 * <p>
 * It works as a proxy: the browser cannot call Steam directly (CORS), so the front-end
 * asks this API and this API asks Steam.
 */
@Service
public class SteamService {

    private final RestClient restClient;

    /**
     * Creates the service with a client pointed to the Steam Store
     *
     * @param builder Builder used to create the {@link RestClient}
     */
    public SteamService(RestClient.Builder builder){
        this.restClient = builder
                .baseUrl("https://store.steampowered.com")
                .build();
    }

    /**
     * Finds the details of a game by its Steam app ID
     *
     * @param appId Steam app ID of the game
     * @return The Steam response with the game details (title, description and cover image)
     * @throws IllegalStateException    If Steam returns an empty response
     * @throws IllegalArgumentException If Steam has no game with the given app ID
     */
    public SteamResponseDTO findGame(Long appId){

        Map<String, SteamResponseDTO> response = restClient.get()
                .uri("/api/appdetails?appids={appId}", appId)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        if (response == null) {
            throw new IllegalStateException("Empty Steam API response for the appId " + appId);
        }

        SteamResponseDTO game = response.get(String.valueOf(appId));

        if (game == null) {
            throw new IllegalArgumentException("Game not find with appId " + appId);
        }

        return game;
    }

    /**
     * Searches games in the Steam Store by name
     *
     * @param name Text to search in the game name
     * @return The games found, with their app ID and image (empty if there are none)
     */
    public SteamSearchResponseDTO searchByName(String name){

        SteamSearchResponseDTO responseDTO = restClient.get()
                .uri("/api/storesearch/?term={name}&cc=br&l=portuguese", name)
                .retrieve()
                .body(SteamSearchResponseDTO.class);

        if(responseDTO == null || responseDTO.items().isEmpty()){
            return new SteamSearchResponseDTO(0, List.of());
        }

        return responseDTO;
    }
}