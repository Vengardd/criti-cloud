package com.krajust.criti_cloud_back.rating;

import com.krajust.criti_cloud_back.media.MediaMapper;
import com.krajust.criti_cloud_back.user.UserMapper;

import java.util.List;

public class RatingMapper {

    public static RatingDTO toDTO(Rating rating) {
        return new RatingDTO(rating.id, UserMapper.toDTO(rating.user), MediaMapper.toDTO(rating.media), rating.rating, rating.source);
    }

    public static Rating toEntity(RatingDTO ratingDTO) {
        return new Rating(ratingDTO.id, UserMapper.toEntity(ratingDTO.user), MediaMapper.toEntity(ratingDTO.media), ratingDTO.rating, ratingDTO.source);
    }

    public static List<RatingDTO> toDTOs(List<Rating> ratings) {
        return ratings.stream().map(RatingMapper::toDTO).toList();
    }

    public static List<Rating> toEntities(List<RatingDTO> ratingDTOS) {
        return ratingDTOS.stream().map(RatingMapper::toEntity).toList();
    }

}
