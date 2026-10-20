package com.br.game_library.service.steam;

import com.br.game_library.dto.steam.SteamResponseDTO;
import com.br.game_library.dto.steam.SteamSearchResponseDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
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
