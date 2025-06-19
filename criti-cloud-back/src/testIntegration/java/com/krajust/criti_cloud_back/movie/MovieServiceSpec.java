package com.krajust.criti_cloud_back.movie;

import com.krajust.criti_cloud_back.CritiCloudBackApplicationIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MovieServiceSpec extends CritiCloudBackApplicationIntegrationTest implements MovieTestData {

    @Autowired
    MovieService movieService;

    @Test
    void create_movie_and_media_entities() {
        // given
        var movie = toCreateMediaDTO();

        // when
        var result = movieService.save(movie.build());

        // then
        assertThat(result).isEqualTo(movie.id(result.id).build());
    }

}
