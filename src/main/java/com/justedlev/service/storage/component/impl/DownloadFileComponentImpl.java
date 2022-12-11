package com.justedlev.service.storage.component.impl;

import com.justedlev.service.storage.component.DownloadFileComponent;
import com.justedlev.service.storage.configuration.properties.JStorageProperties;
import com.justedlev.service.storage.model.response.DownloadFileResponse;
import com.justedlev.service.storage.repository.FileRepository;
import com.justedlev.service.storage.repository.entity.FileEntity;

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

@Component
@RequiredArgsConstructor
public class DownloadFileComponentImpl implements DownloadFileComponent {
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

        switch (contentMediaType) {
            case AUDIO:
            case IMAGE:
            case VIDIO: {
                response.setContentType(MediaType.parseMediaType(entity.getContentType()));
                response.getHeaders()
                        .add(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.inline()
                                .filename(entity.getOriginalFileName())
                                .build().toString());
                break;
            }
            default:
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
        VIDIO("vidio"),
        IMAGE("image"),
        OTHER("other");

        private final String value;
    }
}
