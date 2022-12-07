package com.justedlev.service.storage.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Data
@Configuration
@ConfigurationProperties(prefix = "jstorage")
public class JStorageProperties {
    private Path rootPath;
}
