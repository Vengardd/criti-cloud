package com.krajust.criti_cloud_back.game;

import lombok.Builder;

@Builder
public record GameSearch(String title, String igdbId) {
}
