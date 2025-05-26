package com.krajust.criti_cloud_back.media;

import com.krajust.criti_cloud_back.common.exception.EntityNotExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.krajust.criti_cloud_back.common.entity.EntityType.MEDIA;
import static com.krajust.criti_cloud_back.media.MediaMapper.*;

@Service
public class MediaService {

    private final MediaRepository mediaRepository;

    @Autowired
    public MediaService(MediaSpringRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    public List<MediaDTO> findAllMedia() {
        return toDTOs(mediaRepository.findAll());
    }

    public MediaDTO save(MediaDTO mediaDTO) {
        return toDTO(mediaRepository.save(toEntity(mediaDTO)));
    }

    public MediaDTO getById(UUID id) {
        return toDTO(mediaRepository.findById(id).orElseThrow(() -> new EntityNotExists(MEDIA, id)));
    }
}
