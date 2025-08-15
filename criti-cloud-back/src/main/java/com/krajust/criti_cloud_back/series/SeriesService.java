package com.krajust.criti_cloud_back.series;

import com.krajust.criti_cloud_back.common.entity.EntityType;
import com.krajust.criti_cloud_back.common.exception.EntityNotExists;
import com.krajust.criti_cloud_back.common.exception.EntityNotExistsIdType;
import com.krajust.criti_cloud_back.media.MediaDTO;
import com.krajust.criti_cloud_back.media.MediaService;
import com.krajust.criti_cloud_back.media.ProviderService;
import com.krajust.criti_cloud_back.movie.MovieMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.krajust.criti_cloud_back.common.entity.EntityExternalIdType.IMBD_ID;
import static com.krajust.criti_cloud_back.common.entity.EntityType.MOVIE;
import static com.krajust.criti_cloud_back.media.DetailsType.SERIES;
import static com.krajust.criti_cloud_back.series.SeriesMapper.toDTO;
import static com.krajust.criti_cloud_back.series.SeriesMapper.toEntity;
import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class SeriesService {

    private final Logger log = LoggerFactory.getLogger(SeriesService.class);

    private final SeriesRepository seriesRepository;
    private final MediaService mediaService;
    private final ProviderService<SeriesDTO> seriesProviderService;

    public List<SeriesDTO> findAll() {
        return seriesRepository.findAll().stream()
                .map(SeriesMapper::toDTO)
                .collect(Collectors.toList());
    }

    public SeriesDTO getById(UUID id) {
        return seriesRepository.findById(id)
                .map(SeriesMapper::toDTO)
                .orElseThrow(() -> new EntityNotExists(EntityType.SERIES, id));
    }

    public List<SeriesDTO> search(SeriesSearch searchDTO) {
        if (searchDTO.imbdId() != null || searchDTO.title() != null) {
            if (searchDTO.imbdId() != null) {
                return List.of(searchByIMBDId(searchDTO.imbdId()));
            }
            return seriesRepository.findByTitleContains(searchDTO.title()).stream().map(SeriesMapper::toDTO).toList();
        }
        log.warn("Searching series by searchDTO: {} not supported.", searchDTO);
        return emptyList();
    }

    private SeriesDTO searchByIMBDId(String imbdId) {
        final var seriesLocally = seriesRepository.findByImbdId(imbdId);
        if (seriesLocally.isPresent()) {
            return SeriesMapper.toDTO(seriesLocally.get());
        }
        final var foundSeries = seriesProviderService.findByProviderId(imbdId);
        if (foundSeries.isPresent()) {
            final var savedSeries = save(foundSeries.get());
            log.info("Saved new series with ID: {} and imbdId: {}", savedSeries.id, savedSeries.imbdId);
            return savedSeries;
        }
        throw new EntityNotExists(MOVIE, imbdId, EntityNotExistsIdType.MOVIE_IMBD_ID);
    }

    @Transactional
    public SeriesDTO save(SeriesDTO seriesDTO) {
        final var savedSeries = toDTO(seriesRepository.save(toEntity(seriesDTO)));
        final var mediaBasedOnMovie = createMediaFromSeries(savedSeries);
        final var media = mediaService.save(mediaBasedOnMovie);
        return savedSeries;
    }

    private MediaDTO createMediaFromSeries(SeriesDTO seriesDTO) {
        return MediaDTO.builder()
                .id(seriesDTO.id)
                .name(seriesDTO.title)
                .detailsType(SERIES)
                .detailsId(seriesDTO.id)
                .posterUrl(seriesDTO.posterUrl)
                .externalId(seriesDTO.imbdId)
                .externalIdType(IMBD_ID)
                .build();
    }

}
