package com.krajust.criti_cloud_back.media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/media",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class MediaController {

    private final MediaService mediaService;

    @Autowired
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @GetMapping
    public List<MediaDTO> allMedia() {
        return mediaService.findAllMedia();
    }

    @GetMapping("/{id}")
    public MediaDTO getById(@PathVariable UUID id) {
        return mediaService.getById(id);
    }

    @PostMapping
    public MediaDTO addMedia(@RequestBody MediaDTO media) {
        return mediaService.save(media);
    }

}
