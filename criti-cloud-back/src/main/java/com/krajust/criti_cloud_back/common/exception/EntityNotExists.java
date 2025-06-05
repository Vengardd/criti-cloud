package com.krajust.criti_cloud_back.common.exception;

import com.krajust.criti_cloud_back.common.entity.EntityType;

import java.util.UUID;

public class EntityNotExists extends RuntimeException{

    private EntityType entityType;
    private String id;
    private EntityNotExistsIdType idType;

    public EntityNotExists(EntityType entityType, String id, EntityNotExistsIdType idType) {
        super("Entity " + entityType + " with id: " + id + " of type: " + idType + " not found");
        this.entityType = entityType;
        this.id = id;
        this.idType = idType;
    }

    public EntityNotExists(EntityType entityType, String id) {
        this(entityType, id, EntityNotExistsIdType.ID);
    }

    public EntityNotExists(EntityType entityType, UUID id) {
        this(entityType, id.toString(), EntityNotExistsIdType.ID);
    }

}
