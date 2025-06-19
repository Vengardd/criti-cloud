package com.krajust.criti_cloud_back.movie;

import com.krajust.criti_cloud_back.search.SearchDTO;
import lombok.Builder;

@Builder
public record MovieSearchDTO(String imbdId, String name) {

    public static MovieSearchDTO fromMediaSearchDTO(SearchDTO searchDTO) {
        return MovieSearchDTO.builder()
                .name(searchDTO.title())
                .build();
    }
}
