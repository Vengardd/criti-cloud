package com.krajust.criti_cloud_back.user;

import org.springframework.data.repository.Repository;

import java.util.UUID;

@org.springframework.stereotype.Repository
public interface UserSpringRepository extends UserRepository, Repository<User, UUID> {
}
