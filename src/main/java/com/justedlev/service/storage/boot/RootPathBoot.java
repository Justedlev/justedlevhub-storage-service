package com.justedlev.service.storage.boot;

import com.justedlev.service.storage.properties.JStorageProperties;
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

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (Files.notExists(properties.getRootPath())) {
            Files.createDirectory(properties.getRootPath());
            log.info("'{}' folder was created", properties.getRootPath());
        }
    }
}
