package com.br.game_library.dto.steam;

import java.util.List;

public record SteamSearchResponseDTO(

        Integer total,
        List<SteamSearchItemDTO> items
) {}
