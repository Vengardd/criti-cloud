package com.krajust.criti_cloud_back.game;

import java.util.List;

public class GameMapper {

    public static GameDTO toDTO(Game game) {
        return GameDTO.builder()
                .id(game.id)
                .title(game.title)
                .summary(game.summary)
                .igdbId(game.igdbId)
                .posterUrl(game.posterUrl)
                .build();
    }

    public static Game toEntity(GameDTO dto) {
        return new Game(dto.id,  dto.title, dto.summary, dto.igdbId, dto.posterUrl);
    }

    public static List<GameDTO> toDTOs(List<Game> gameList) {
        return gameList.stream().map(GameMapper::toDTO).toList();
    }

}
