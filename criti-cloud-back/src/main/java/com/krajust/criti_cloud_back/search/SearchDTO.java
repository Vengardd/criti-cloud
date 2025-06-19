package com.krajust.criti_cloud_back.search;

import com.krajust.criti_cloud_back.common.entity.MediaType;
import lombok.Builder;

@Builder
public record SearchDTO(MediaType type, Boolean external, String title) {
}
