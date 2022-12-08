package com.justedlev.service.storage.component.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
    private final ModelMapper defaultMapper;

    @Override
    @SneakyThrows
    public DownloadFileResponse download(UUID fileId) {
        FileEntity entity = fileRepository.findById(fileId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("File %s not found", fileId)));
        var path = properties.getRootPath() + File.separator + entity.getId();
        var inputStream = new FileInputStream(path);
        var inputStreamResource = new InputStreamResource(inputStream);
        var res = defaultMapper.map(entity, DownloadFileResponse.class);
        res.setResource(inputStreamResource);
        res.getHeaders()
                .add(HttpHeaders.CONTENT_LENGTH, String.valueOf(entity.getSize()));

        if (entity.getContentType().contains("image") || entity.getContentType().contains("video")
                || entity.getContentType().contains("audio")) {
            res.setContentType(MediaType.parseMediaType(entity.getContentType()));
            res.getHeaders()
                    .add(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.inline()
                            .filename(entity.getName())
                            .build().toString());
        } else {
            res.getHeaders()
                    .add(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                            .filename(entity.getName())
                            .build().toString());
        }

        return res;
    }
}
