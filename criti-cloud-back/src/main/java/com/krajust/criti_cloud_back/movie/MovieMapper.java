package com.krajust.criti_cloud_back.movie;

import com.krajust.criti_cloud_back.integration.ombd.OMBDSingleMovieResponse;
import com.krajust.criti_cloud_back.integration.ombd.OMBDSingleShortMovieResponse;

public class MovieMapper {

    public static MovieDTO toDTO(Movie movie) {
        return MovieDTO.builder()
                .id(movie.id)
                .title(movie.title)
                .year(movie.year)
                .runtime(movie.runtime)
                .director(movie.director)
                .plot(movie.plot)
                .imbdId(movie.imbdId)
                .posterUrl(movie.posterUrl)
                .build();
    }

    public static Movie toEntity(MovieDTO movieDTO) {
        return new Movie(movieDTO.id, movieDTO.title, movieDTO.year, movieDTO.runtime, movieDTO.director, movieDTO.plot, movieDTO.imbdId, movieDTO.posterUrl);
    }

    public static MovieDTO toDTO(OMBDSingleMovieResponse movieResponse) {
        final var runtime = Integer.parseInt(movieResponse.Runtime().split(" ")[0]);
        return MovieDTO.builder()
                .title(movieResponse.Title())
                .year(movieResponse.Year())
                .runtime(runtime)
                .director(movieResponse.Director())
                .plot(movieResponse.Plot())
                .imbdId(movieResponse.imdbID())
                .posterUrl(movieResponse.Poster())
                .build();
    }

}