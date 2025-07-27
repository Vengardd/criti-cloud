package com.krajust.criti_cloud_back.game;

import com.krajust.criti_cloud_back.common.entity.EntityExternalIdType;
import com.krajust.criti_cloud_back.common.exception.EntityNotExists;
import com.krajust.criti_cloud_back.common.exception.EntityNotExistsIdType;
import com.krajust.criti_cloud_back.media.DetailsType;
import com.krajust.criti_cloud_back.media.MediaDTO;
import com.krajust.criti_cloud_back.media.MediaService;
import com.krajust.criti_cloud_back.media.ProviderService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.krajust.criti_cloud_back.common.entity.EntityExternalIdType.IGDB_ID;
import static com.krajust.criti_cloud_back.common.entity.EntityType.GAME;
import static com.krajust.criti_cloud_back.game.GameMapper.toDTO;
import static com.krajust.criti_cloud_back.game.GameMapper.toDTOs;
import static com.krajust.criti_cloud_back.game.GameMapper.toEntity;

@Service
public class GameService {

    private final Logger log = LoggerFactory.getLogger(GameService.class);

    private final GameRepository gameRepository;
    private final MediaService mediaService;
    private final ProviderService<GameDTO> gameProvider;

    @Autowired
    public GameService(GameSpringRepository gameRepository, MediaService mediaService, ProviderService<GameDTO> gameProvider) {
        this.gameRepository = gameRepository;
        this.mediaService = mediaService;
        this.gameProvider = gameProvider;
    }

    public GameDTO getById(UUID id) {
        return toDTO(gameRepository.findById(id).orElseThrow());
    }

    public Collection<GameDTO> findAll() {
        return toDTOs(gameRepository.findAll());
    }

    public List<GameDTO> search(GameSearch search) {
        if (search.title() != null || search.igdbId() != null) {
            if (search.title() != null) {
                return toDTOs(gameRepository.findByTitleContains(search.title()));
            }
            return List.of(searchByIgdbId(search.igdbId()));
        }
        return toDTOs(gameRepository.findAll());
    }

    public GameDTO searchByIgdbId(String igdbId) {
        final var game = gameRepository.findByIgdbId(igdbId);
        if (game.isPresent()) {
            return toDTO(game.get());
        }
        final var foundGame = gameProvider.findByProviderId(igdbId);
        if (foundGame.isPresent()) {
            final var savedGame = save(foundGame.get());
            log.info("Saved new game with ID: {} and igdbId: {}", savedGame.id, savedGame.igdbId);
            return savedGame;
        }
        throw new EntityNotExists(GAME, igdbId, EntityNotExistsIdType.GAME_IGDB_ID);
    }

    @Transactional
    protected GameDTO save(GameDTO gameDTO) {
        final var savedGame = toDTO(gameRepository.saveAndFlush(toEntity(gameDTO)));
        final var mediaBasedOnMovie = createMediaFromGame(savedGame);
        final var media = mediaService.save(mediaBasedOnMovie);
        return savedGame;
    }

    private MediaDTO createMediaFromGame(GameDTO savedGame) {
        return MediaDTO.builder()
                .id(savedGame.id)
                .name(savedGame.title)
                .detailsType(DetailsType.GAME)
                .detailsId(savedGame.id)
                .posterUrl(savedGame.posterUrl)
                .externalId(savedGame.igdbId)
                .externalIdType(IGDB_ID)
                .build();
    }
}
