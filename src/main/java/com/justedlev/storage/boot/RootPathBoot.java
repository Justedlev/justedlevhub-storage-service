package com.justedlev.storage.boot;

import com.justedlev.storage.properties.JStorageProperties;
import com.justedlev.storage.repository.FileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;

@Slf4j
@Component
@RequiredArgsConstructor
public class RootPathBoot implements ApplicationRunner {
    private final JStorageProperties properties;
    private final FileRepository fileRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (Files.notExists(properties.getRootPath())) {
            Files.createDirectory(properties.getRootPath());
            fileRepository.deleteAll();
            log.info("Created root directory: {}", properties.getRootPath().toAbsolutePath());
        }
    }
}
