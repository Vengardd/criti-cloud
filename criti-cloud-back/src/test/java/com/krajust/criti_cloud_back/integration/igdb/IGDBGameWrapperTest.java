package com.krajust.criti_cloud_back.integration.igdb;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


class IGDBGameWrapperTest {

    private final String clientId = "randomClientId";
    private final String clientSecret = "randomClientSecret";

    @Disabled
    @Test
    public void fetches_games_by_name() {
        var instance = new IGDBGameProvider(clientId, clientSecret);
        instance.searchByTitle("Bloodborne", 10, 1);
    }

}