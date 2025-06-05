package com.krajust.criti_cloud_back.media;

import com.krajust.criti_cloud_back.common.entity.EntityExternalIdType;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class MediaDTO {

    public UUID id;
    public String name;
    public String posterUrl;
    public DetailsType detailsType;
    public UUID detailsId;
    public EntityExternalIdType externalIdType;
    public String externalId;

}
