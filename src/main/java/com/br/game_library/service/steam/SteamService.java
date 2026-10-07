package com.br.game_library.service.steam;

import com.br.game_library.dto.steam.SteamResponseDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class SteamService {

    private final RestClient restClient;

    public SteamService(RestClient.Builder builder){
        this.restClient = builder
                .baseUrl("https://store.steampowered.com")
                .build();
    }

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
}
