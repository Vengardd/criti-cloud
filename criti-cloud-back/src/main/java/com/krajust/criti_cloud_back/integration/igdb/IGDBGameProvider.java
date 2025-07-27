package com.krajust.criti_cloud_back.integration.igdb;

import com.api.igdb.apicalypse.APICalypse;
import com.api.igdb.exceptions.RequestException;
import com.api.igdb.request.IGDBWrapper;
import com.api.igdb.request.ProtoRequestKt;
import com.api.igdb.request.TwitchAuthenticator;
import com.api.igdb.utils.TwitchToken;
import com.krajust.criti_cloud_back.game.GameDTO;
import com.krajust.criti_cloud_back.media.MediaDTO;
import com.krajust.criti_cloud_back.media.ProviderService;
import lombok.extern.slf4j.Slf4j;
import proto.Cover;
import proto.Game;

import java.util.List;
import java.util.Optional;

import static com.krajust.criti_cloud_back.common.HelpingMethods.checkRequired;
import static com.krajust.criti_cloud_back.common.HelpingMethods.some;
import static com.krajust.criti_cloud_back.common.entity.EntityExternalIdType.IGDB_ID;
import static com.krajust.criti_cloud_back.media.DetailsType.GAME;

@Slf4j
public class IGDBGameProvider implements ProviderService<GameDTO> {

    private final TwitchAuthenticator authenticator;
    private final TwitchToken token;
    private final IGDBWrapper wrapper;

    public IGDBGameProvider(String clientId, String clientSecret) {
        this.authenticator = TwitchAuthenticator.INSTANCE;
        this.token = authenticator.requestTwitchToken(clientId, clientSecret);
        this.wrapper = IGDBWrapper.INSTANCE;
        this.wrapper.setCredentials(clientId, checkRequired(token.getAccess_token(), "IGDB request token"));
    }

    @Override
    public Optional<GameDTO> findByProviderId(String igdbId) {
        try {
            final var gameQuery = new APICalypse()
                    .fields("*")
                    .where("id = " + igdbId);
            final var game = ProtoRequestKt.games(wrapper, gameQuery).getFirst();
            final var coverQuery = new APICalypse()
                    .fields("*")
                    .where("id = " + game.getCover().getId());
            final var cover = ProtoRequestKt.covers(wrapper, coverQuery).getFirst();
            final var hdUrl = replaceUrlToHd(cover);
            return some(GameDTO.builder()
                    .title(game.getName())
                    .summary(game.getSummary())
                    .igdbId(Long.toString(game.getId()))
                    .posterUrl(hdUrl)
                    .build());
        } catch (RequestException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MediaDTO> searchByTitle(String title, int size, int page) {
        try {
            final var query = new APICalypse()
                    .fields("*")
                    .limit(size)
                    .offset(size * (page-1))
                    .search(title);
            final var games = ProtoRequestKt.games(wrapper, query);
            final var coversList = games.stream().map(game -> Long.toString(game.getCover().getId())).toList();
            final var covers = covers(size, page, coversList);
            return games.stream().map(game -> MediaDTO.builder()
                    .name(game.getName())
                    .posterUrl(posterUrlFromAllCovers(game, covers))
                    .detailsType(GAME)
                    .externalIdType(IGDB_ID)
                    .externalId(Long.toString(game.getId()))
                    .build()).toList();
        } catch (RequestException e) {
            log.error("Failed to search for games by title: {}. Response: {}", title, e.getMessage(), e);
            return List.of();
        }
    }

    private String posterUrlFromAllCovers(Game game, List<Cover> covers) {
        return covers.stream().filter(cover -> cover.getId() == game.getCover().getId()).map(this::replaceUrlToHd).findFirst().orElse(null);
    }

    private List<Cover> covers(int size, int page, List<String> coversList) throws RequestException {
        try {
            final var coverQuery = new APICalypse()
                    .fields("*")
                    .limit(size)
                    .offset(size * (page-1))
                    .where("id = (" + String.join(",", coversList) + ")");
            return ProtoRequestKt.covers(wrapper, coverQuery);
        } catch (RequestException e) {
            log.error("Failed to search for covers by ids: {}. Response: {}", coversList, e.getMessage(), e);
            return List.of();
        }
    }

    private String replaceUrlToHd(Cover cover) {
        return cover.getUrl().replace("t_thumb", "t_720p");
    }

}
