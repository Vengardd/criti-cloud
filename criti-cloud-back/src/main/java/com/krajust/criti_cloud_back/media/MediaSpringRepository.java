package com.krajust.criti_cloud_back.media;

import org.springframework.data.repository.Repository;

import java.util.UUID;

@org.springframework.stereotype.Repository
public interface MediaSpringRepository extends MediaRepository, Repository<Media, UUID> {

}
