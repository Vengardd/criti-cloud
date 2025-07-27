package com.krajust.criti_cloud_back.rating;

import com.krajust.criti_cloud_back.common.exception.EntityNotExists;
import com.krajust.criti_cloud_back.media.MediaService;
import com.krajust.criti_cloud_back.user.UserDTO;
import com.krajust.criti_cloud_back.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.krajust.criti_cloud_back.common.entity.EntityType.MEDIA;
import static com.krajust.criti_cloud_back.common.entity.EntityType.RATING;
import static com.krajust.criti_cloud_back.common.entity.EntityType.USER;
import static com.krajust.criti_cloud_back.rating.RatingMapper.toDTO;
import static com.krajust.criti_cloud_back.rating.RatingMapper.toDTOs;
import static com.krajust.criti_cloud_back.rating.RatingMapper.toEntity;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final UserService userService;
    private final MediaService mediaService;

    public RatingService(RatingRepository ratingRepository, UserService userService, MediaService mediaService) {
        this.ratingRepository = ratingRepository;
        this.userService = userService;
        this.mediaService = mediaService;
    }

    public List<RatingDTO> findAllRatings() {
        return toDTOs(ratingRepository.findAll());
    }

    public RatingDTO getById(UUID id) {
        return toDTO(ratingRepository.findById(id).orElseThrow(() -> new EntityNotExists(RATING, id)));
    }

    public RatingDTO save(RatingController.NewRatingRequest newRatingRequest, UserDTO userDTO) {
        final var media = mediaService.findById(newRatingRequest.mediaId());
        if (media.isEmpty()) {
            throw new EntityNotExists(MEDIA, newRatingRequest.mediaId());
        }
        final var user = userService.findById(userDTO.id);
        if (user.isEmpty()) {
            throw new EntityNotExists(USER, userDTO.id);
        }
        final var rating = RatingDTO.builder()
                .media(media.get())
                .user(user.get())
                .rating(newRatingRequest.rating())
                .source(newRatingRequest.source())
                .build();
        return toDTO(ratingRepository.save(toEntity(rating)));
    }

    public List<RatingDTO> search(RatingSearch search) {
        if (search.mediaId() != null) {
            return toDTOs(ratingRepository.findAllByMediaId(search.mediaId()));
        }
        if (search.userId() != null) {
            return toDTOs(ratingRepository.findAllByUserId(search.userId()));
        }
        throw new IllegalArgumentException("Search must have userId or mediaId");
    }
}
