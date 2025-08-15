package com.krajust.criti_cloud_back.integration.ombd;

import com.krajust.criti_cloud_back.media.MediaDTO;
import com.krajust.criti_cloud_back.media.ProviderService;
import com.krajust.criti_cloud_back.series.SeriesDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OMBDSeriesProvider implements ProviderService<SeriesDTO> {

    private final static String SERIES_TYPE = "series";

    private final OMBDProvider ombdProvider;

    public OMBDSeriesProvider(OMBDProvider ombdProvider) {
        this.ombdProvider = ombdProvider;
    }

    @Override
    public Optional<SeriesDTO> findByProviderId(String imbdId) {
        return ombdProvider.findSeriesByProviderId(imbdId);
    }

    @Override
    public List<MediaDTO> searchByTitle(String title, int page, int size) {
        return ombdProvider.searchByTitle(title, SERIES_TYPE, size, page);
    }
}
