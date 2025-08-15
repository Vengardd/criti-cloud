package com.krajust.criti_cloud_back.series;

import com.krajust.criti_cloud_back.common.entity.EntityExternalIdType;
import com.krajust.criti_cloud_back.integration.ombd.OMBDSingleSeriesResponse;
import com.krajust.criti_cloud_back.media.DetailsType;
import com.krajust.criti_cloud_back.media.MediaDTO;

public class SeriesMapper {

    public static SeriesDTO toDTO(Series series) {
        return new SeriesDTO(
                series.getId(),
                series.getTitle(),
                series.getYear(),
                series.getSeasons(),
                series.getPlot(),
                series.getImbdId(),
                series.getPosterUrl()
        );
    }

    public static Series toEntity(SeriesDTO seriesDTO) {
        return new Series(
                seriesDTO.getId(),
                seriesDTO.getTitle(),
                seriesDTO.getYear(),
                seriesDTO.getSeasons(),
                seriesDTO.getPlot(),
                seriesDTO.getImbdId(),
                seriesDTO.getPosterUrl()
        );
    }

    public static SeriesDTO toDTO(OMBDSingleSeriesResponse seriesResponse) {
        return SeriesDTO.builder()
                .title(seriesResponse.Title())
                .year(seriesResponse.Year())
                .seasons(seriesResponse.TotalSeasons())
                .plot(seriesResponse.Plot())
                .imbdId(seriesResponse.imdbID())
                .posterUrl(seriesResponse.Poster())
                .build();
    }

    public static MediaDTO toDTO(SeriesDTO seriesDTO) {
        return MediaDTO.builder()
                .id(seriesDTO.getId())
                .name(seriesDTO.getTitle())
                .posterUrl(seriesDTO.getPosterUrl())
                .detailsType(DetailsType.SERIES)
                .detailsId(seriesDTO.getId())
                .externalId(seriesDTO.getImbdId())
                .externalIdType(EntityExternalIdType.IMBD_ID)
                .build();
    }
}
