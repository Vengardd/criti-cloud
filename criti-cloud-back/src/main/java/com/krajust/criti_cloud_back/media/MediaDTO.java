package com.krajust.criti_cloud_back.media;

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
    public DetailsType detailsType;
    public UUID detailsId;

}
