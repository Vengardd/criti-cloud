package com.krajust.criti_cloud_back.movie;

import com.krajust.criti_cloud_back.media.MediaService;
import com.krajust.criti_cloud_back.media.ProviderService;
import org.junit.jupiter.api.Test;

import static com.krajust.criti_cloud_back.movie.MovieMapper.toEntity;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MovieServiceTest implements MovieTestData {

    private final MovieSpringRepository movieRepository = mock(MovieSpringRepository.class);
    private final MediaService mediaService = mock(MediaService.class);
    private final ProviderService<MovieDTO> movieProviderService = mock(ProviderService.class);

    MovieService movieService = new MovieService(movieRepository, mediaService, movieProviderService);

    @Test
    void adds_new_movie_and_creates_media() {
        // given
        var movieBuilder = toCreateMediaDTO();
        var movie = movieBuilder.build();
        var movieEntity = toEntity(movie);
        var savedMovie = toEntity(movieBuilder.id(randomUUID()).build());
        when(movieRepository.save(movieEntity)).thenReturn(savedMovie);

        // when
        var result = movieService.save(movie);

        // then
        verify(movieRepository).save(movieEntity);
        assertThat(result).isNotNull();
        assertThat(result.title).isEqualTo(title);
        assertThat(result.posterUrl).isEqualTo(posterUrl);
        assertThat(result.year).isEqualTo(year);
        assertThat(result.runtime).isEqualTo(runtime);
        assertThat(result.director).isEqualTo(director);
        assertThat(result.plot).isEqualTo(plot);
        assertThat(result.imbdId).isEqualTo(imbdId);
        assertThat(result.posterUrl).isEqualTo(posterUrl);
    }
}