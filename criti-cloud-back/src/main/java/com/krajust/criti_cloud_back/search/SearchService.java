package com.krajust.criti_cloud_back.search;

import com.krajust.criti_cloud_back.common.exception.BadRequestException;
import com.krajust.criti_cloud_back.game.GameDTO;
import com.krajust.criti_cloud_back.game.GameSearch;
import com.krajust.criti_cloud_back.game.GameService;
import com.krajust.criti_cloud_back.media.MediaDTO;
import com.krajust.criti_cloud_back.media.MediaService;
import com.krajust.criti_cloud_back.media.ProviderService;
import com.krajust.criti_cloud_back.movie.MovieDTO;
import com.krajust.criti_cloud_back.movie.MovieSearch;
import com.krajust.criti_cloud_back.movie.MovieService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.krajust.criti_cloud_back.media.MediaMapper.toDTOsFromGames;
import static com.krajust.criti_cloud_back.media.MediaMapper.toDTOsFromMovies;

@Service
public class SearchService {

    private final ProviderService<MovieDTO> movieProviderService;
    private final ProviderService<GameDTO> gameProviderService;
    private final MediaService mediaService;
    private final MovieService movieService;
    private final GameService gameService;

    public SearchService(MovieService movieService, MediaService mediaService, GameService gameService, ProviderService<MovieDTO> movieProviderService, ProviderService<GameDTO> gameProviderService) {
        this.mediaService = mediaService;
        this.movieService = movieService;
        this.gameService = gameService;
        this.movieProviderService = movieProviderService;
        this.gameProviderService = gameProviderService;
    }

    public List<MediaDTO> search(SearchDTO searchDTO) {
        if (!searchDTO.external()) {
            return handleInternalSearch(searchDTO);
        }
        return handleExternalSearch(searchDTO);
    }

    private List<MediaDTO> handleInternalSearch(SearchDTO searchDTO) {
        switch (searchDTO.type()) {
            case MEDIA -> {
                return mediaService.findAllByNameContains(searchDTO.title());
            }
            case MOVIE -> {
                return toDTOsFromMovies(movieService.search(MovieSearch.builder().title(searchDTO.title()).build()));
            }
            case GAME -> {
                return toDTOsFromGames(gameService.search(GameSearch.builder().title(searchDTO.title()).build()));
            }
            default -> throw new BadRequestException("Provided media type is not supported");
        }
    }

    private List<MediaDTO> handleExternalSearch(SearchDTO searchDTO) {
        switch (searchDTO.type()) {
            case MEDIA -> throw new BadRequestException("Searching external media is not supported");
            case MOVIE -> {
                return movieProviderService.searchByTitle(searchDTO.title(), searchDTO.size(), searchDTO.page());
            }
            case GAME -> {
                return gameProviderService.searchByTitle(searchDTO.title(), searchDTO.size(), searchDTO.page());
            }
            default -> throw new BadRequestException("Provided media type is not supported");
        }
    }
}
