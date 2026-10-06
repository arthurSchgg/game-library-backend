package com.br.game_library.service.steam;

import com.br.game_library.dto.steam.SteamResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class SteamService {

    private final RestClient restClient;

    public SteamService(RestClient.builder builder){
        this.restClient = builder
                .baseUrl("https://store.steampowered.com")
                .build();
    }

    public SteamResponseDTO findGame(Long appId){

        return RestClient.get()
                .uri("/api/appdetails?appids={appId}", appId)
                .retrieve()
                .body(SteamResponseDTO.class);
    }
}
