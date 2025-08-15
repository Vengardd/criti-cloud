package com.krajust.criti_cloud_back.series;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class SeriesDTO {
    public UUID id;
    public String title;
    public String year;
    public int seasons;
    public String plot;
    public String imbdId;
    public String posterUrl;
}
