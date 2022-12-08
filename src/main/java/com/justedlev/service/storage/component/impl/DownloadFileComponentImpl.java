package com.justedlev.service.storage.component.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;

import com.justedlev.service.storage.component.DownloadFileComponent;
import com.justedlev.service.storage.config.properties.JStorageProperties;
import com.justedlev.service.storage.model.response.DownloadFileResponse;
import com.justedlev.service.storage.repository.FileRepository;
import com.justedlev.service.storage.repository.entity.FileEntity;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Component
@RequiredArgsConstructor
public class DownloadFileComponentImpl implements DownloadFileComponent {
    private final JStorageProperties properties;
    private final FileRepository fileRepository;

    @Override
    @SneakyThrows
    public DownloadFileResponse download(UUID fileId) {
        FileEntity entity = fileRepository.findById(fileId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("File %s not found", fileId)));
        var path = properties.getRootPath() + File.separator + entity.getId();
        var inputStream = new FileInputStream(path);
        var inputStreamResource = new InputStreamResource(inputStream);

        return DownloadFileResponse.builder()
                .contentType(entity.getContentType())
                .extension(entity.getExtension())
                .size(entity.getSize())
                .name(entity.getName())
                .resource(inputStreamResource)
                .build();
    }
}
