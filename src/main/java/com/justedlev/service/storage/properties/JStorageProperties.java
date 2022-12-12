package com.justedlev.service.storage.properties;

import lombok.Data;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@Data
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "jstorage")
public class JStorageProperties {
    private Path rootPath;
    private String downloadFilename;
}
