package com.krajust.criti_cloud_back.media;

import com.krajust.criti_cloud_back.common.exception.EntityNotExists;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.krajust.criti_cloud_back.common.entity.EntityType.MEDIA;
import static com.krajust.criti_cloud_back.media.MediaMapper.toDTO;
import static com.krajust.criti_cloud_back.media.MediaMapper.toDTOs;
import static com.krajust.criti_cloud_back.media.MediaMapper.toEntity;
import static java.util.UUID.randomUUID;

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

    @Transactional
    public MediaDTO save(MediaDTO mediaDTO) {
        if (mediaDTO.id == null) {
            mediaDTO.id = randomUUID();
        }
        return toDTO(mediaRepository.save(toEntity(mediaDTO)));
    }

    public Optional<MediaDTO> findById(UUID id) {
        return mediaRepository.findById(id).map(MediaMapper::toDTO);
    }

    public MediaDTO getById(UUID id) {
        return toDTO(mediaRepository.findById(id).orElseThrow(() -> new EntityNotExists(MEDIA, id)));
    }

    public List<MediaDTO> findAllByNameContains(String name) {
        return toDTOs(mediaRepository.findAllByNameContains(name));
    }
}
