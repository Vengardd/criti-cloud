package com.krajust.criti_cloud_back.movie;

import org.springframework.data.repository.Repository;

import java.util.UUID;

@org.springframework.stereotype.Repository
public interface MovieSpringRepository extends MovieRepository, Repository<Movie, UUID> {
}
