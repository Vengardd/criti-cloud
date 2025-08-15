package com.krajust.criti_cloud_back.series;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/series")
public class SeriesController {

    private final SeriesService seriesService;

    public SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @GetMapping("/{id}")
    public SeriesDTO getSeriesById(@PathVariable UUID id) {
        return seriesService.getById(id);
    }

    @GetMapping
    public Collection<SeriesDTO> search(
            @RequestParam(required = false) String imbdId,
            @RequestParam(required = false) String title) {
        if (imbdId == null && title == null) {
            return seriesService.findAll();
        }
        final var searchDTO = SeriesSearch.builder()
                .imbdId(imbdId)
                .title(title)
                .build();
        return seriesService.search(searchDTO);
    }

}
