package com.krajust.criti_cloud_back.integration.ombd;

import java.util.List;

public record OMBDMultiMovieResponse(List<OMBDSingleShortMovieResponse> Search) {
}
