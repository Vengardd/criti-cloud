package com.krajust.criti_cloud_back.movie;

import com.krajust.criti_cloud_back.common.exception.EntityNotExists;
import com.krajust.criti_cloud_back.common.exception.EntityNotExistsIdType;
import com.krajust.criti_cloud_back.media.DetailsType;
import com.krajust.criti_cloud_back.media.MediaDTO;
import com.krajust.criti_cloud_back.media.MediaService;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.krajust.criti_cloud_back.common.entity.EntityType.MOVIE;
import static com.krajust.criti_cloud_back.movie.MovieMapper.toDTO;
import static com.krajust.criti_cloud_back.movie.MovieMapper.toEntity;
import static java.util.Collections.emptyList;

@Service
public class MovieService {

    private final Logger log = LoggerFactory.getLogger(MovieService.class);

    private final MovieRepository movieRepository;
    private final MediaService mediaService;
    private final MovieProviderService movieProviderService;

    public MovieService(MovieSpringRepository movieRepository, MediaService mediaService, MovieProviderService movieProviderService) {
        this.movieRepository = movieRepository;
        this.mediaService = mediaService;
        this.movieProviderService = movieProviderService;
    }

    public MovieDTO getById(UUID id) {
        log.info("Searching movie by ID: {}", id);
        return toDTO(movieRepository.findById(id).orElseThrow(() -> new EntityNotExists(MOVIE, id)));
    }

    public Collection<MovieDTO> search(MovieSearchDTO searchDTO) {
        if (searchDTO.imbdId() != null) {
            return List.of(searchByIMBDId(searchDTO.imbdId()));
        }
        log.warn("Searching movie by searchDTO: {} not supported.", searchDTO);
        return emptyList();
    }

    public MovieDTO searchByIMBDId(String imbdId) {
        final var movieLocally = movieRepository.findByImbdId(imbdId);
        if (movieLocally.isPresent()) {
            return toDTO(movieLocally.get());
        }
        final var foundMovie = movieProviderService.getByImbdId(imbdId);
        if (foundMovie.isPresent()) {
            final var savedMovie = save(foundMovie.get());
            log.info("Saved new movie with ID: {} and imbdId: {}", savedMovie.id, savedMovie.imbdId);
            return savedMovie;
        }
        throw new EntityNotExists(MOVIE, imbdId, EntityNotExistsIdType.MOVIE_IMBD_ID);
    }

    @Transactional
    public MovieDTO save(MovieDTO movie) {
        final var savedMovie = toDTO(movieRepository.save(toEntity(movie)));
        final var mediaBasedOnMovie = createMediaFromMovie(savedMovie);
        final var media = mediaService.save(mediaBasedOnMovie);
        return savedMovie;
    }

    private MediaDTO createMediaFromMovie(MovieDTO movieDTO) {
        return MediaDTO.builder()
                .name(movieDTO.title)
                .detailsType(DetailsType.MOVIE)
                .detailsId(movieDTO.id)
                .build();
    }

    public List<MovieDTO> findAll() {
        return movieRepository.findAll().stream().map(MovieMapper::toDTO).toList();
    }
}
