package com.krajust.criti_cloud_back.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping(path = "/movies", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping(value = "/{id}")
    public MovieDTO getById(@PathVariable UUID id) {
        return movieService.getById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public MovieDTO addMovie(@RequestBody MovieDTO movie) {
        return movieService.save(movie);
    }

    @GetMapping
    public Collection<MovieDTO> search(
            @RequestParam(required = false) String imbdId,
            @RequestParam(required = false) String title) {
        if (imbdId == null && title == null) {
            return movieService.findAll();
        }
        final var searchDTO = MovieSearch.builder()
                .imbdId(imbdId)
                .title(title)
                .build();
        return movieService.search(searchDTO);
    }
}
