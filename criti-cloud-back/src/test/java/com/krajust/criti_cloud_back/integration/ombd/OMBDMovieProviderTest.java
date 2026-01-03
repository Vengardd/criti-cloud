package com.krajust.criti_cloud_back.integration.ombd;

import com.krajust.criti_cloud_back.media.MediaDTO;
import com.krajust.criti_cloud_back.movie.MovieTestData;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.krajust.criti_cloud_back.common.HelpingMethods.some;
import static com.krajust.criti_cloud_back.common.entity.EntityExternalIdType.IMBD_ID;
import static com.krajust.criti_cloud_back.media.DetailsType.MOVIE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

class OMBDMovieProviderTest implements MovieTestData {

    private RestTemplate restTemplate = mock(RestTemplate.class);

    private String ombdUrl = "OMBD_URL";
    private String ombdApiKey = "OMBD_API_KEY";

    private OMBDProvider ombdProvider = new OMBDProvider(restTemplate, ombdUrl, ombdApiKey);
    private OMBDMovieProvider movieProvider = new OMBDMovieProvider(ombdProvider);

    private String imbdId = "tt9243804";
    private String title = "Green";

    @Test
    void fetches_by_id() {
        // given
        when(restTemplate.getForEntity(ombdUrl + "/?apiKey=" + ombdApiKey + "&i=" + imbdId, OMBDSingleMovieResponse.class))
                .thenReturn(new ResponseEntity<>(new OMBDSingleMovieResponse(title, year, runtime + " Min", director, plot, imbdId, posterUrl), OK));

        // when
        var movie = movieProvider.findByProviderId(imbdId);

        // then
        assertThat(movie).isPresent().isEqualTo(
                some(toCreateMediaDTO()
                        .title(title)
                        .imbdId(imbdId)
                        .build())
        );
    }

    @Test
    void returns_nothing_when_movie_not_found() {
        // given
        when(restTemplate.getForEntity(ombdUrl + "/?apiKey=" + ombdApiKey + "&i=" + imbdId, OMBDSingleMovieResponse.class))
                .thenReturn(new ResponseEntity<>(null, NOT_FOUND));

        // when
        var movie = movieProvider.findByProviderId(imbdId);

        // then
        assertThat(movie).isEmpty();
    }

    @Test
    void fetches_all_by_name() {
        // given
        var singleShortMovie = new OMBDSingleShortMovieResponse(title, Integer.toString(year), imbdId, "movie", posterUrl);
        var url = ombdUrl + "/?apiKey=" + ombdApiKey + "&s=" + title + "&type=movie";
        when(restTemplate.getForEntity(url, OMBDMultiMovieResponse.class))
                .thenReturn(new ResponseEntity<>(new OMBDMultiMovieResponse(List.of(singleShortMovie), 1, "True"), OK));

        // when
        var movies = movieProvider.searchByTitle(title, 20, 1);

        // then
        assertThat(movies).hasSize(1).containsExactly(MediaDTO.builder()
                .name(title)
                .posterUrl(posterUrl)
                .detailsType(MOVIE)
                .externalIdType(IMBD_ID)
                .externalId(imbdId)
                .build());
    }

}