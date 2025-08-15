package com.krajust.criti_cloud_back.series;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SeriesRepository extends JpaRepository<Series, UUID> {
    List<Series> findByTitleContains(String title);

    Optional<Series> findByImbdId(String imbdId);
}
