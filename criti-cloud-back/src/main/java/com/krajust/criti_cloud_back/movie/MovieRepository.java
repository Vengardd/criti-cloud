package com.krajust.criti_cloud_back.movie;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MovieRepository {

    Optional<Movie> findById(UUID id);

    Movie save(Movie entity);

    Movie saveAndFlush(Movie entity);

    Optional<Movie> findByImbdId(String imbdId);

    List<Movie> findByTitleContains(String title);

    List<Movie> findAll();
}
