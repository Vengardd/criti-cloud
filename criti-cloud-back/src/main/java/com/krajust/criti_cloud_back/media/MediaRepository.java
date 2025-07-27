package com.krajust.criti_cloud_back.media;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MediaRepository {

    List<Media> findAll();

    Media save(Media media);

    Media saveAndFlush(Media entity);

    Optional<Media> findById(UUID id);

    List<Media> findAllByNameContains(String name);
}
