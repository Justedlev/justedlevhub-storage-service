package com.justedlev.storage.component.impl;

import com.justedlev.storage.component.FileDownloader;
import com.justedlev.storage.model.response.DownloadFileResponse;
import com.justedlev.storage.properties.JStorageProperties;
import com.justedlev.storage.repository.FileRepository;
import com.justedlev.storage.repository.entity.FileEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FileDownloaderImpl implements FileDownloader {
    private final JStorageProperties properties;
    private final FileRepository fileRepository;
    private final ModelMapper defaultMapper;

    @Override
    public DownloadFileResponse download(String fileName) {
        FileEntity entity = fileRepository.findByFileName(fileName)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("File %s not found", fileName)));
        var res = defaultMapper.map(entity, DownloadFileResponse.class);
        setResource(res, entity);
        setHeaders(res, entity);

        return res;
    }

    @SneakyThrows
    private void setResource(DownloadFileResponse response, FileEntity entity) {
        var path = properties.getRootPath().resolve(entity.getFileName()).toFile();

        if (!path.exists()) {
            fileRepository.delete(entity);
            throw new FileNotFoundException(String.format("File %s not found", entity.getFileName()));
        }

        var inputStream = new FileInputStream(path);
        var inputStreamResource = new InputStreamResource(inputStream);
        response.setResource(inputStreamResource);
    }

    private void setHeaders(DownloadFileResponse response, FileEntity entity) {
        response.getHeaders()
                .add(HttpHeaders.CONTENT_LENGTH, String.valueOf(entity.getSize()));
        var contentMediaType = calculateContentMediaType(entity.getContentType());

        if (Objects.requireNonNull(contentMediaType) == ContentMediaType.AUDIO ||
                contentMediaType == ContentMediaType.IMAGE ||
                contentMediaType == ContentMediaType.VIDEO) {
            response.setContentType(MediaType.parseMediaType(entity.getContentType()));
            response.getHeaders()
                    .add(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.inline()
                            .filename(entity.getOriginalFileName())
                            .build().toString());
        } else {
            response.getHeaders()
                    .add(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                            .filename(entity.getOriginalFileName())
                            .build().toString());
        }
    }

    private ContentMediaType calculateContentMediaType(String contentType) {
        return Arrays.stream(ContentMediaType.values())
                .filter(current -> contentType.contains(current.getValue()))
                .findFirst()
                .orElse(ContentMediaType.OTHER);
    }

    @Getter
    @RequiredArgsConstructor
    private enum ContentMediaType {
        AUDIO("audio"),
        VIDEO("video"),
        IMAGE("image"),
        OTHER("other");

        private final String value;
    }
}
