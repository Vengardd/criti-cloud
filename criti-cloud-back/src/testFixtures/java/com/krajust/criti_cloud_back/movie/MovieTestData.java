package com.krajust.criti_cloud_back.movie;

import static java.util.UUID.randomUUID;

public interface MovieTestData {

    String title = "randomTitle";
    int year = 2137;
    int runtime = 420;
    String director = "randomDirector";
    String plot = "Plot about some random movie when random things happen";
    String imbdId = "tt0111161";
    String posterUrl = "https://m.media-amazon.com/images/M/MV5BNjRjNmU1M2ItNDU4Ni00ZGY2LTlmNzItY2MxYmY3OTllZjMwXkEyXkFqcGc@._V1_SX300.jpg";

    default MovieDTO.MovieDTOBuilder createdMediaDTO() {
        return toCreateMediaDTO()
                .id(randomUUID());
    }

    default MovieDTO.MovieDTOBuilder toCreateMediaDTO() {
        return MovieDTO.builder()
                .title(title)
                .year(year)
                .runtime(runtime)
                .director(director)
                .plot(plot)
                .imbdId(imbdId)
                .posterUrl(posterUrl);
    }

}
