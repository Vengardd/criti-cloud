package com.krajust.criti_cloud_back.game;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameRepository {

    Optional<Game> findById(UUID id);

    Game save(Game game);

    Game saveAndFlush(Game game);

    Optional<Game> findByIgdbId(String igdbId);

    List<Game> findByTitleContains(String title);

    List<Game> findAll();
}
