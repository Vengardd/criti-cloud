package com.krajust.criti_cloud_back.media;

import java.util.List;
import java.util.Optional;

public interface ProviderService<T> {

    Optional<T> findByProviderId(String providerId);

    List<MediaDTO> searchByTitle(String title, int page, int size);
}
