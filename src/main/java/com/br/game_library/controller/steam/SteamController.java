package com.br.game_library.controller.steam;

import com.br.game_library.dto.steam.SteamResponseDTO;
import com.br.game_library.dto.steam.SteamSearchResponseDTO;
import com.br.game_library.service.steam.SteamService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("v1/steam")
public class SteamController {

    private final SteamService service;

    public SteamController(SteamService service) {
        this.service = service;
    }

    @GetMapping("/{appId}")
    public ResponseEntity<SteamResponseDTO> findGame(@PathVariable Long appId){
        return ResponseEntity.ok(service.findGame(appId));
    }

    @GetMapping("/{search}")
    public ResponseEntity<SteamSearchResponseDTO> searchByName(@RequestParam String  nome){
        return ResponseEntity.ok(service.searchByName(nome));
    }
}
