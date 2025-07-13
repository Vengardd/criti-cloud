package com.krajust.criti_cloud_back.media;

import com.krajust.criti_cloud_back.game.GameDTO;
import com.krajust.criti_cloud_back.integration.ombd.OMBDSingleShortMovieResponse;
import com.krajust.criti_cloud_back.movie.MovieDTO;
import com.krajust.criti_cloud_back.movie.MovieMapper;

import java.util.Collection;
import java.util.List;

import static com.krajust.criti_cloud_back.common.entity.EntityExternalIdType.IGDB_ID;
import static com.krajust.criti_cloud_back.common.entity.EntityExternalIdType.IMBD_ID;

public class MediaMapper {

    public static MediaDTO toDTO(Media media) {
        return new MediaDTO(media.id, media.name, media.posterUrl, media.detailsType, media.detailsId, media.externalIdType, media.externalId);
    }

    public static List<MediaDTO> toDTOs(List<Media> medias) {
        return medias.stream().map(MediaMapper::toDTO).toList();
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

    public static List<MediaDTO> toDTOsFromMovies(List<MovieDTO> movies) {
        return movies.stream().map(MediaMapper::toDTO).toList();
    }

    public static MediaDTO toDTO(MovieDTO movie) {
        return MediaDTO.builder()
                .id(movie.id)
                .name(movie.title)
                .posterUrl(movie.posterUrl)
                .detailsType(DetailsType.MOVIE)
                .detailsId(movie.id)
                .externalId(movie.imbdId)
                .externalIdType(IMBD_ID)
                .build();
    }

    public static List<MediaDTO> toDTOsFromGames(List<GameDTO> games) {
        return games.stream().map(MediaMapper::toDTO).toList();
    }

    public static MediaDTO toDTO(GameDTO game) {
        return MediaDTO.builder()
                .id(game.id)
                .name(game.title)
                .posterUrl(game.posterUrl)
                .detailsType(DetailsType.GAME)
                .detailsId(game.id)
                .externalId(game.igdbId)
                .externalIdType(IGDB_ID)
                .build();
    }
}
