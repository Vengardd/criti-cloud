package com.krajust.criti_cloud_back.rating;

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
@RequestMapping(path = "/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping
    public List<RatingDTO> search(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID mediaId
    ) {
        if (userId == null && mediaId == null) {
            return ratingService.findAllRatings();
        }
        final var search = new RatingSearch(userId, mediaId);
        return ratingService.search(search);
    }

    @GetMapping("/{id}")
    public RatingDTO getById(@PathVariable UUID id) {
        return ratingService.getById(id);
    }

    @PostMapping
    public RatingDTO addRating(@RequestBody RatingDTO ratingDTO) {
        return ratingService.save(ratingDTO);
    }

}
