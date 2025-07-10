package com.krajust.criti_cloud_back.rating;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RatingRepository {

    List<Rating> findAll();

    Rating save(Rating rating);

    Optional<Rating> findById(UUID id);

    List<Rating> findAllByUserId(UUID userId);

    List<Rating> findAllByMediaId(UUID mediaId);

}
