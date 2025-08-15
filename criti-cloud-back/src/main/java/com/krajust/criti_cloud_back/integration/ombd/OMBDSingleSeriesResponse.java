package com.krajust.criti_cloud_back.integration.ombd;

public record OMBDSingleSeriesResponse(String Title, String Year, String Runtime, int TotalSeasons, String Plot, String imdbID, String Poster) {
}
