package com.krajust.criti_cloud_back.media;

import com.krajust.criti_cloud_back.common.entity.EntityExternalIdType;

import java.util.UUID;

import static com.krajust.criti_cloud_back.common.entity.EntityExternalIdType.IMBD_ID;
import static com.krajust.criti_cloud_back.media.DetailsType.MOVIE;
import static java.util.UUID.randomUUID;

public interface MediaTestData {

    String name = "name";
    String posterUrl = "posterUrl";
    DetailsType detailsType = MOVIE;
    UUID detailsId = randomUUID();
    EntityExternalIdType externalIdType = IMBD_ID;
    String externalId = "externalId";

    default MediaDTO.MediaDTOBuilder toCreateMedia() {
        return MediaDTO.builder()
                .name(name)
                .posterUrl(posterUrl)
                .detailsType(detailsType)
                .detailsId(detailsId)
                .externalIdType(externalIdType)
                .externalId(externalId);
    }


}
