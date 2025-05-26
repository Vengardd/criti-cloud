package com.krajust.criti_cloud_back.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class MovieDTO {

    public UUID id;
    public String title;
    public int year;
    public int runtime;
    public String director;
    public String plot;
    public String imbdId;
    public String posterUrl;

}
