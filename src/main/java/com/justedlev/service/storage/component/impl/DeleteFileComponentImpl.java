package com.justedlev.service.storage.component.impl;

import com.justedlev.service.storage.component.DeleteFileComponent;
import com.justedlev.service.storage.configuration.properties.JStorageProperties;
import com.justedlev.service.storage.repository.FileRepository;
import com.justedlev.service.storage.repository.entity.FileEntity;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.nio.file.Files;

@Component
@RequiredArgsConstructor
public class DeleteFileComponentImpl implements DeleteFileComponent {
    private final FileRepository fileRepository;
    private final JStorageProperties properties;

    @Override
    public Boolean deleteByName(String fileName) {
        return fileRepository.findByName(fileName)
                .map(this::delete)
                .orElse(Boolean.FALSE);
    }

    @SneakyThrows
    private Boolean delete(FileEntity entity) {
        var file = properties.getRootPath().resolve(entity.getName());
        Files.delete(file);

        if (!file.toFile().exists()) {
            fileRepository.delete(entity);
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }
}