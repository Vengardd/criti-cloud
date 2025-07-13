package com.krajust.criti_cloud_back.game;

import org.springframework.data.repository.Repository;

import java.util.UUID;

@org.springframework.stereotype.Repository
public interface GameSpringRepository extends GameRepository, Repository<Game, UUID> {
}
