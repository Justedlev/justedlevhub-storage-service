package com.justedlev.storage.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.nio.file.Path;

@Data
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {
    private Path rootPath;
    private String downloadFilename;
}
