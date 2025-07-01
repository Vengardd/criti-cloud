package com.krajust.criti_cloud_back.media;

import com.krajust.criti_cloud_back.common.entity.MediaType;
import com.krajust.criti_cloud_back.search.SearchDTO;
import com.krajust.criti_cloud_back.search.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/media")
public class MediaController {

    private final MediaService mediaService;
    private final SearchService searchService;

    public MediaController(MediaService mediaService, SearchService searchService) {
        this.mediaService = mediaService;
        this.searchService = searchService;
    }

    @GetMapping("/{id}")
    public MediaDTO getById(@PathVariable UUID id) {
        return mediaService.getById(id);
    }

    @PostMapping
    public MediaDTO addMedia(@RequestBody MediaDTO media) {
        return mediaService.save(media);
    }

    @GetMapping
    public List<MediaDTO> search(@RequestParam(required = false) MediaType type,
                                 @RequestParam(required = false, defaultValue = "false") Boolean external,
                                 @RequestParam(required = false) String title) {
        if (type == null && !external  && title == null) {
            return mediaService.findAllMedia();
        }
        final var searchDTO = SearchDTO.builder()
                .type(type)
                .external(external)
                .title(title)
                .build();
        return searchService.search(searchDTO);
    }

}
