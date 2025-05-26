package com.krajust.criti_cloud_back.movie;

import java.util.Optional;
import java.util.UUID;

public interface MovieRepository {

    Optional<Movie> findById(UUID id);

    Movie save(Movie entity);
}
