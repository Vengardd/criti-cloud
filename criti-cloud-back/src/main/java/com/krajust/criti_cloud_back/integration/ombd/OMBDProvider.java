package com.krajust.criti_cloud_back.integration.ombd;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.krajust.criti_cloud_back.media.DetailsType;
import com.krajust.criti_cloud_back.media.MediaDTO;
import com.krajust.criti_cloud_back.media.MediaMapper;
import com.krajust.criti_cloud_back.movie.MovieDTO;
import com.krajust.criti_cloud_back.series.SeriesDTO;
import com.krajust.criti_cloud_back.series.SeriesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static com.krajust.criti_cloud_back.common.HelpingMethods.checkRequired;
import static com.krajust.criti_cloud_back.common.HelpingMethods.some;
import static com.krajust.criti_cloud_back.movie.MovieMapper.toDTO;
import static java.util.Optional.empty;

public class OMBDProvider {

    private final static Logger log = LoggerFactory.getLogger(OMBDProvider.class);

    private final String OMBD_SINGLE_MEDIA_URL_TEMPLATE = "%s/?apiKey=%s&i=%s";
    private final String OMBD_MULTI_MEDIA_URL_TEMPLATE = "%s/?apiKey=%s&s=%s&type=%s";

    private final Cache<String, OMBDMultiMovieResponse> ombdMultiMovieResponseCache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofDays(7))
            .maximumSize(200)
            .build();

    private final RestTemplate restTemplate;
    private final String ombdUrl;
    private final String ombdApiKey;

    public OMBDProvider(RestTemplate restTemplate, String ombdUrl, String ombdApiKey) {
        this.restTemplate = restTemplate;
        this.ombdUrl = ombdUrl;
        this.ombdApiKey = ombdApiKey;
    }

    public Optional<MovieDTO> findMovieByProviderId(String imbdId) {
        final var url = OMBD_SINGLE_MEDIA_URL_TEMPLATE.formatted(ombdUrl, ombdApiKey, imbdId);
        final var response = restTemplate.getForEntity(url, OMBDSingleMovieResponse.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error("Failed to get movie from OMBD. Response: {}", response);
            return empty();
        }
        final var ombdMovieResponse = checkRequired(response.getBody(), "ombdResponseBody");
        final var movieDTO = toDTO(ombdMovieResponse);
        return Optional.of(movieDTO);
    }

    public Optional<SeriesDTO> findSeriesByProviderId(String imbdId) {
        final var url = OMBD_SINGLE_MEDIA_URL_TEMPLATE.formatted(ombdUrl, ombdApiKey, imbdId);
        final var response = restTemplate.getForEntity(url, OMBDSingleSeriesResponse.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error("Failed to get series from OMBD. Response: {}", response);
            return empty();
        }
        final var ombdSeriesResponse = checkRequired(response.getBody(), "ombdResponseBody");
        final var seriesDTO = SeriesMapper.toDTO(ombdSeriesResponse);
        return Optional.of(seriesDTO);
    }

    //ToDo implement paging for movies
    public List<MediaDTO> searchByTitle(String title, String type, int size, int page) {
        final var response = getMoviesFromOmbd(title, type);
        final var detailsType = type.equals("movie") ? DetailsType.MOVIE : DetailsType.SERIES;
        return response.map(OMBDMultiMovieResponse::Search).orElse(List.of()).stream().toList().stream().map(singleShortMovieResponse -> MediaMapper.toDTO(singleShortMovieResponse, detailsType)).limit(size).toList();
    }

    private Optional<OMBDMultiMovieResponse> getMoviesFromOmbd(String title, String type) {
        final var url = OMBD_MULTI_MEDIA_URL_TEMPLATE.formatted(ombdUrl, ombdApiKey, title, type);
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
