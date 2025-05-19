package com.krajust.criti_cloud_back.common.exception;

import com.krajust.criti_cloud_back.common.entity.EntityType;

import java.util.UUID;

public class EntityNotExists extends RuntimeException{

    private EntityType entityType;
    private UUID id;

    public EntityNotExists(EntityType entityType, UUID id) {
        super("Entity " + entityType + " with id: " + id + " not found");
        this.entityType = entityType;
        this.id = id;
    }

}
