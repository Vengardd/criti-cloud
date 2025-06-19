package com.krajust.criti_cloud_back.configuration;

import com.krajust.criti_cloud_back.integration.ombd.OMBDMovieProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeansConfiguration {

    @Bean
    public OMBDMovieProvider ombdMovieProvider(
            RestTemplate restTemplate,
            @Value("${integration.ombd.url}") String url,
            @Value("${integration.ombd.api-key}") String apiKey
    ) {
        return new OMBDMovieProvider(restTemplate, url, apiKey);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
