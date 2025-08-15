package com.krajust.criti_cloud_back.configuration;

import com.krajust.criti_cloud_back.integration.igdb.IGDBGameProvider;
import com.krajust.criti_cloud_back.integration.ombd.OMBDProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeansConfiguration {

    @Bean
    public OMBDProvider ombdProvider(
            RestTemplate restTemplate,
            @Value("${integration.ombd.url}") String url,
            @Value("${integration.ombd.api-key}") String apiKey) {
        return new OMBDProvider(restTemplate, url, apiKey);
    }

    @Bean
    public IGDBGameProvider igdbGameProvider(
            @Value("${integration.igdb.client-id}") String clientId,
            @Value("${integration.igdb.client-secret}") String apiKey
    ) {
        return new IGDBGameProvider(clientId, apiKey);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
