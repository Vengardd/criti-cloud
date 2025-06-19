package com.krajust.criti_cloud_back.integration.ombd;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.krajust.criti_cloud_back.media.MediaDTO;
import com.krajust.criti_cloud_back.media.MediaMapper;
import com.krajust.criti_cloud_back.movie.MovieDTO;
import com.krajust.criti_cloud_back.movie.MovieProviderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.krajust.criti_cloud_back.common.HelpingMethods.checkRequired;
import static com.krajust.criti_cloud_back.common.HelpingMethods.some;
import static com.krajust.criti_cloud_back.movie.MovieMapper.toDTO;
import static java.util.Optional.empty;

@Slf4j
public class OMBDMovieProvider implements MovieProviderService {

    private final String OMBD_SINGLE_MOVIE_URL_TEMPLATE = "%s/?apiKey=%s&i=%s";
    private final String OMBD_MULTI_MOVIE_URL_TEMPLATE = "%s/?apiKey=%s&s=%s";

    private final Cache<String, OMBDMultiMovieResponse> ombdMultiMovieResponseCache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofDays(7))
            .maximumSize(200)
            .build();

    private final RestTemplate restTemplate;
    private final String ombdUrl;
    private final String ombdApiKey;

    public OMBDMovieProvider(RestTemplate restTemplate, String ombdUrl, String ombdApiKey) {
        this.restTemplate = restTemplate;
        this.ombdUrl = ombdUrl;
        this.ombdApiKey = ombdApiKey;
    }

    @Override
    public Optional<MovieDTO> getByImbdId(String imbdId) {
        final var url = OMBD_SINGLE_MOVIE_URL_TEMPLATE.formatted(ombdUrl, ombdApiKey, imbdId);
        final var response = restTemplate.getForEntity(url, OMBDSingleMovieResponse.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error("Failed to get movie from OMBD. Response: {}", response);
            return empty();
        }
        final var ombdMovieResponse = checkRequired(response.getBody(), "ombdResponseBody");
        final var movieDTO = toDTO(ombdMovieResponse);
        return Optional.of(movieDTO);
    }

    @Override
    public List<MediaDTO> searchByTitle(String title) {
        final var response = getMoviesFromOmbd(title);
        return response.map(OMBDMultiMovieResponse::Search).orElse(List.of()).stream().toList().stream().map(MediaMapper::toDTO).toList();
    }

    private Optional<OMBDMultiMovieResponse> getMoviesFromOmbd(String title) {
        final var url = OMBD_MULTI_MOVIE_URL_TEMPLATE.formatted(ombdUrl, ombdApiKey, title);
        if (ombdMultiMovieResponseCache.getIfPresent(url) != null) {
            return some(ombdMultiMovieResponseCache.getIfPresent(url));
        }
        log.info("Getting movies from OMBD by title: {}", title);
        final var response = restTemplate.getForEntity(url, OMBDMultiMovieResponse.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error("Failed to get movies from OMBD. Response: {}", response);
            return empty();
        }
        ombdMultiMovieResponseCache.put(url, checkRequired(response.getBody(), "OMBDMultiMovieResponse"));
        return some(response.getBody());
    }

}
