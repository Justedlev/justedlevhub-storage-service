package com.justedlev.service.storage.component.impl;

import com.justedlev.service.storage.component.DownloadFileComponent;
import com.justedlev.service.storage.config.properties.JStorageProperties;
import com.justedlev.service.storage.model.response.DownloadFileResponse;
import com.justedlev.service.storage.repository.FileRepository;
import com.justedlev.service.storage.repository.entity.FileEntity;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Component
@RequiredArgsConstructor
public class DownloadFileComponentImpl implements DownloadFileComponent {
    private final JStorageProperties properties;
    private final FileRepository fileRepository;
    private final ModelMapper defaultMapper;

    @Override
    public DownloadFileResponse download(String fileName) {
        FileEntity entity = fileRepository.findByName(fileName)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("File %s not found", fileName)));
        var res = defaultMapper.map(entity, DownloadFileResponse.class);
        setResource(res, entity);
        setHeaders(res, entity);

        return res;
    }

    @SneakyThrows
    private void setResource(DownloadFileResponse response, FileEntity entity) {
        var path = properties.getRootPath()
                .resolve(File.separator + entity.getName())
                .toFile();

        if (!path.exists()) {
            fileRepository.delete(entity);
            throw new FileNotFoundException(String.format("File %s not found", entity.getName()));
        }

        var inputStream = new FileInputStream(path);
        var inputStreamResource = new InputStreamResource(inputStream);
        response.setResource(inputStreamResource);
    }

    private void setHeaders(DownloadFileResponse response, FileEntity entity) {
        response.getHeaders()
                .add(HttpHeaders.CONTENT_LENGTH, String.valueOf(entity.getSize()));

        if (entity.getContentType().contains("image") || entity.getContentType().contains("video")
                || entity.getContentType().contains("audio")) {
            response.setContentType(MediaType.parseMediaType(entity.getContentType()));
            response.getHeaders()
                    .add(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.inline()
                            .filename(entity.getName())
                            .build().toString());
        } else {
            response.getHeaders()
                    .add(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                            .filename(entity.getName())
                            .build().toString());
        }
    }
}
