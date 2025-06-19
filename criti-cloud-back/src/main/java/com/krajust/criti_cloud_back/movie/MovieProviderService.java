package com.krajust.criti_cloud_back.movie;

import com.krajust.criti_cloud_back.media.MediaDTO;

import java.util.List;
import java.util.Optional;

public interface MovieProviderService {

    Optional<MovieDTO> getByImbdId(String imbdId);

    List<MediaDTO> searchByTitle(String title);
}
