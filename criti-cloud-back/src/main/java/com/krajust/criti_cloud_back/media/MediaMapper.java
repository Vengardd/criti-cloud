package com.krajust.criti_cloud_back.media;

import java.util.List;

public class MediaMapper {

    public static MediaDTO fromEntity(Media media) {
        return new MediaDTO(media.id, media.name, media.detailsType, media.detailsId);
    }

    public static List<MediaDTO> fromEntities(List<Media> mediaList) {
        return mediaList.stream().map(MediaMapper::fromEntity).toList();
    }

    public static Media toEntity(MediaDTO mediaDTO) {
        return new Media(mediaDTO.id, mediaDTO.name, mediaDTO.detailsType, mediaDTO.detailsId);
    }

    public static List<Media> toEntities(List<MediaDTO> mediaDTOS) {
        return mediaDTOS.stream().map(MediaMapper::toEntity).toList();
    }
}
