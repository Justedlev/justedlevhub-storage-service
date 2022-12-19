package com.justedlev.storage.client.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Setter
@Getter
@ConfigurationProperties(prefix = "justedlev-service.storage.client")
public class StorageFeignClientProperties {
    private String url;
    private Duration connectTimeout = Duration.of(10, ChronoUnit.SECONDS);
    private Duration readTimeout = Duration.of(2, ChronoUnit.MINUTES);
}
