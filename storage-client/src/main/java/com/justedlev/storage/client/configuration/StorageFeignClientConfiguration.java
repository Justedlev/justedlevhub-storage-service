package com.justedlev.storage.client.configuration;

import feign.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class StorageFeignClientConfiguration {
    private final StorageFeignClientProperties properties;

    @Bean
    public Request.Options requestOptions() {
        return new Request.Options(
                properties.getConnectTimeout().toMillis(),
                TimeUnit.MILLISECONDS,
                properties.getReadTimeout().toMillis(),
                TimeUnit.MILLISECONDS,
                Boolean.FALSE
        );
    }
}
