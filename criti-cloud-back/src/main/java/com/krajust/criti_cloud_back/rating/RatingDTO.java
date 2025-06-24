package com.krajust.criti_cloud_back.rating;

import com.krajust.criti_cloud_back.media.MediaDTO;
import com.krajust.criti_cloud_back.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class RatingDTO {

    public UUID id;
    public UserDTO user;
    public MediaDTO media;
    public BigDecimal rating;
    public RatingSource source;

}
