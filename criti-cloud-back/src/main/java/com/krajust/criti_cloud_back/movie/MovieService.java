package com.krajust.criti_cloud_back.movie;

import com.krajust.criti_cloud_back.common.exception.EntityNotExists;
import com.krajust.criti_cloud_back.media.DetailsType;
import com.krajust.criti_cloud_back.media.MediaDTO;
import com.krajust.criti_cloud_back.media.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.krajust.criti_cloud_back.common.entity.EntityType.MOVIE;
import static com.krajust.criti_cloud_back.movie.MovieMapper.toDTO;
import static com.krajust.criti_cloud_back.movie.MovieMapper.toEntity;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final MediaService mediaService;

    @Autowired
    public MovieService(MovieSpringRepository movieRepository, MediaService mediaService) {
        this.movieRepository = movieRepository;
        this.mediaService = mediaService;
    }

    public MovieDTO getById(UUID id) {
        return toDTO(movieRepository.findById(id).orElseThrow(() -> new EntityNotExists(MOVIE, id)));
    }

    @Transactional
    public MovieDTO save(MovieDTO movie) {
        final var savedMovie = toDTO(movieRepository.save(toEntity(movie)));
        final var mediaBasedOnMovie = createMediaFromMovie(savedMovie);
        mediaService.save(mediaBasedOnMovie);
        return savedMovie;
    }

    private MediaDTO createMediaFromMovie(MovieDTO movieDTO) {
        return MediaDTO.builder()
                .name(movieDTO.title)
                .detailsType(DetailsType.MOVIE)
                .detailsId(movieDTO.id)
                .build();
    }

}
