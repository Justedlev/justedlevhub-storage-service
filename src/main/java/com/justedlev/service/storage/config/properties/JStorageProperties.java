package com.justedlev.service.storage.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.nio.file.Path;

@Data
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "jstorage")
public class JStorageProperties {
    private Path rootPath;
}
