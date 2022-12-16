package com.justedlev.storage.client.configuration;

import feign.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class StorageFeignClientConfiguration {
    @Value("${justedlev-service.storage.read-timeout:1m}")
    private Duration readTimeout;
    @Value("${justedlev-service.storage.connect-timeout:10s}")
    private Duration connectTimeout;

    @Bean
    public Request.Options requestOptions() {
        return new Request.Options(
                connectTimeout.get(ChronoUnit.MILLIS),
                TimeUnit.MILLISECONDS,
                readTimeout.get(ChronoUnit.MILLIS),
                TimeUnit.MILLISECONDS,
                Boolean.FALSE
        );
    }
}
