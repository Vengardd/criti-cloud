package com.krajust.criti_cloud_back.integration.ombd;

import com.krajust.criti_cloud_back.media.MediaDTO;
import com.krajust.criti_cloud_back.media.MediaMapper;
import com.krajust.criti_cloud_back.movie.MovieDTO;
import com.krajust.criti_cloud_back.movie.MovieProviderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static com.krajust.criti_cloud_back.common.HelpingMethods.checkRequired;
import static com.krajust.criti_cloud_back.movie.MovieMapper.toDTO;

@Slf4j
public class OMBDMovieProvider implements MovieProviderService {

    private final String OMBD_SINGLE_MOVIE_URL_TEMPLATE = "%s/?apiKey=%s&i=%s";
    private final String OMBD_MULTI_MOVIE_URL_TEMPLATE = "%s/?apiKey=%s&s=%s";

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
            return Optional.empty();
        }
        final var ombdMovieResponse = checkRequired(response.getBody(), "ombdResponseBody");
        final var movieDTO = toDTO(ombdMovieResponse);
        return Optional.of(movieDTO);
    }

    @Override
    public List<MediaDTO> searchByTitle(String title) {
        final var url = OMBD_MULTI_MOVIE_URL_TEMPLATE.formatted(ombdUrl, ombdApiKey, title);
        final var response = restTemplate.getForEntity(url, OMBDMultiMovieResponse.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error("Failed to get movies from OMBD. Response: {}", response);
            return List.of();
        }
        final var ombdMovieResponse = checkRequired(response.getBody(), "ombdResponseBody");
        return ombdMovieResponse.Search().stream().map(MediaMapper::toDTO).toList();
    }

}
