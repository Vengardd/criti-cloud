package com.krajust.criti_cloud_back.rating;

import com.krajust.criti_cloud_back.common.exception.EntityNotExists;
import com.krajust.criti_cloud_back.media.MediaService;
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

    public RatingDTO save(RatingDTO ratingDTO) {
        validateRating(ratingDTO);
        return toDTO(ratingRepository.save(toEntity(ratingDTO)));
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

    private void validateRating(RatingDTO ratingDTO) {
        if (mediaService.findById(ratingDTO.media.id).isEmpty()) {
            throw new EntityNotExists(MEDIA, ratingDTO.media.id);
        }
        if (userService.findById(ratingDTO.user.id).isEmpty()) {
            throw new EntityNotExists(USER, ratingDTO.user.id);
        }
    }
}
