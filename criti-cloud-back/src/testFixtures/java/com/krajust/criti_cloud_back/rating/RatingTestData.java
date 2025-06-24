package com.krajust.criti_cloud_back.rating;

import com.krajust.criti_cloud_back.media.MediaDTO;
import com.krajust.criti_cloud_back.user.UserDTO;

import java.math.BigDecimal;
import java.util.UUID;

public interface RatingTestData {

    BigDecimal rating = BigDecimal.valueOf(6.5);
    RatingSource ratingSource = RatingSource.APP;

    default RatingDTO.RatingDTOBuilder toCreateRating(UUID userId, UUID mediaId) {
        return RatingDTO.builder()
                .user(UserDTO.builder().id(userId).build())
                .media(MediaDTO.builder().id(mediaId).build())
                .rating(rating)
                .source(ratingSource);
    }

}
