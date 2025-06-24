package com.krajust.criti_cloud_back.rating;

import org.springframework.data.repository.Repository;

import java.util.UUID;

@org.springframework.stereotype.Repository
public interface RatingSpringRepository extends RatingRepository, Repository<Rating, UUID> {

}
