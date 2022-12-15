package com.justedlev.storage.component.impl;

import com.justedlev.storage.component.DeleteFileComponent;
import com.justedlev.storage.properties.StorageProperties;
import com.justedlev.storage.repository.FileRepository;
import com.justedlev.storage.repository.entity.FileEntity;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.nio.file.Files;

@Component
@RequiredArgsConstructor
public class DeleteFileComponentImpl implements DeleteFileComponent {
    private final FileRepository fileRepository;
    private final StorageProperties properties;

    @Override
    public Boolean deleteByName(String fileName) {
        return fileRepository.findByFileName(fileName)
                .map(this::delete)
                .orElse(Boolean.FALSE);
    }

    @SneakyThrows
    private Boolean delete(FileEntity entity) {
        var file = properties.getRootPath().resolve(entity.getFileName());
        Files.delete(file);

        if (!file.toFile().exists()) {
            fileRepository.delete(entity);
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }
}
