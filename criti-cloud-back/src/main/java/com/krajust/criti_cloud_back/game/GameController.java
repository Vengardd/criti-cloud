package com.krajust.criti_cloud_back.game;

import com.krajust.criti_cloud_back.movie.MovieSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping(path = "/games", produces = MediaType.APPLICATION_JSON_VALUE)
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping(value = "/{id}")
    public GameDTO getById(@PathVariable UUID id) {
        return gameService.getById(id);
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Collection<GameDTO> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String igdbId
    ) {
        if (igdbId == null && title == null) {
            return gameService.findAll();
        }
        final var searchDTO = GameSearch.builder()
                .title(title)
                .igdbId(igdbId)
                .build();
        return gameService.search(searchDTO);
    }

}
