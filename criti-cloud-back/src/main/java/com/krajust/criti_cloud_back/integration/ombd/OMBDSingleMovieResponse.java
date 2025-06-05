package com.krajust.criti_cloud_back.integration.ombd;

public record OMBDSingleMovieResponse(String Title, int Year, String Runtime, String Director, String Plot, String imdbID, String Poster) {
}
