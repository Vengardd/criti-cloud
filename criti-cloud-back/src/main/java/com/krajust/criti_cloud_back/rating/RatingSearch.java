package com.krajust.criti_cloud_back.rating;

import java.util.UUID;

public record RatingSearch(UUID userId, UUID mediaId) {

    public RatingSearch(UUID userId, UUID mediaId) {
        if (userId == null && mediaId == null) {
            throw new IllegalArgumentException("At least one of userId or mediaId must be provided");
        }
        if (userId != null && mediaId != null) {
            throw new IllegalArgumentException("One of userId or mediaId must be provided");
        }
        this.userId = userId;
        this.mediaId = mediaId;
    }
}
