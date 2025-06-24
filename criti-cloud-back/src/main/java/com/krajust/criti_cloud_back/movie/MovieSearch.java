package com.krajust.criti_cloud_back.movie;

import com.krajust.criti_cloud_back.search.SearchDTO;
import lombok.Builder;

@Builder
public record MovieSearch(String imbdId, String name) {

    public static MovieSearch fromMediaSearchDTO(SearchDTO searchDTO) {
        return MovieSearch.builder()
                .name(searchDTO.title())
                .build();
    }
}
