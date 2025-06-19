package com.krajust.criti_cloud_back.media;

import com.krajust.criti_cloud_back.common.entity.EntityExternalIdType;
import com.krajust.criti_cloud_back.integration.ombd.OMBDSingleShortMovieResponse;
import com.krajust.criti_cloud_back.movie.MovieDTO;

import java.util.List;

import static com.krajust.criti_cloud_back.common.entity.EntityExternalIdType.IMBD_ID;

public class MediaMapper {

    public static MediaDTO toDTO(Media media) {
        return new MediaDTO(media.id, media.name, media.posterUrl, media.detailsType, media.detailsId, media.externalIdType, media.externalId);
    }

    public static List<MediaDTO> toDTOs(List<Media> mediaList) {
        return mediaList.stream().map(MediaMapper::toDTO).toList();
    }

    public static Media toEntity(MediaDTO mediaDTO) {
        return new Media(mediaDTO.id, mediaDTO.name, mediaDTO.posterUrl, mediaDTO.detailsType, mediaDTO.detailsId, mediaDTO.externalIdType, mediaDTO.externalId);
    }

    public static List<Media> toEntities(List<MediaDTO> mediaDTOS) {
        return mediaDTOS.stream().map(MediaMapper::toEntity).toList();
    }

    public static MediaDTO toDTO(OMBDSingleShortMovieResponse movieResponse) {
        return MediaDTO.builder()
                .name(movieResponse.Title())
                .posterUrl(movieResponse.Poster())
                .detailsType(DetailsType.MOVIE)
                .externalIdType(IMBD_ID)
                .externalId(movieResponse.imdbID())
                .build();
    }
}
