package com.krajust.criti_cloud_back.search;

import com.krajust.criti_cloud_back.common.exception.BadRequestException;
import com.krajust.criti_cloud_back.media.MediaDTO;
import com.krajust.criti_cloud_back.media.MediaService;
import com.krajust.criti_cloud_back.movie.MovieProviderService;
import com.krajust.criti_cloud_back.movie.MovieService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    private final MovieService movieService;
    private final MovieProviderService movieProviderService;
    private final MediaService mediaService;

    public SearchService(MovieService movieService, MediaService mediaService, MovieProviderService movieProviderService) {
        this.movieService = movieService;
        this.mediaService = mediaService;
        this.movieProviderService = movieProviderService;
    }

    public List<MediaDTO> search(SearchDTO searchDTO) {
        if (!searchDTO.external()) {
            return handleInternalSearch(searchDTO);
        }
        return handleExternalSearch(searchDTO);
    }

    private List<MediaDTO> handleInternalSearch(SearchDTO searchDTO) {
        switch (searchDTO.type()) {
            case MEDIA -> { return mediaService.findAllByNameContains(searchDTO.title()); }
            case MOVIE -> throw new BadRequestException("Searching internal movie by title is not implemented yet");
            default -> throw new BadRequestException("Provided media type is not supported");
        }
    }

    private List<MediaDTO> handleExternalSearch(SearchDTO searchDTO) {
        switch (searchDTO.type()) {
            case MEDIA -> throw new BadRequestException("Searching external media is not supported");
            case MOVIE -> { return movieProviderService.searchByTitle(searchDTO.title()); }
            default -> throw new BadRequestException("Provided media type is not supported");
        }
    }
}
