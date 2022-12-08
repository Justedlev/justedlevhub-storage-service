package com.justedlev.service.storage.component.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.justedlev.service.storage.component.UploadFileComponent;
import com.justedlev.service.storage.config.properties.JStorageProperties;
import com.justedlev.service.storage.config.properties.ServiceProperties;
import com.justedlev.service.storage.constant.EndpointConstant;
import com.justedlev.service.storage.model.response.UploadFileResponse;
import com.justedlev.service.storage.repository.FileRepository;
import com.justedlev.service.storage.repository.entity.FileEntity;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Component
@RequiredArgsConstructor
public class UploadFileComponentImpl implements UploadFileComponent {
    private final JStorageProperties properties;
    private final ServiceProperties serviceProperties;
    private final FileRepository fileRepository;

    @Override
    public UploadFileResponse upload(MultipartFile file) {
        var fileEntity = saveToRepository(file);
        saveFileToDir(file, fileEntity);
        var fileUrl = getUri(fileEntity);

        return UploadFileResponse.builder()
                .url(fileUrl)
                .contentType(file.getContentType())
                .extension(fileEntity.getExtension())
                .name(fileEntity.getName())
                .build();
    }

    @SneakyThrows
    private void saveFileToDir(MultipartFile file, FileEntity fileEntity) {
        Path copyLocation = Paths.get(properties.getRootPath() + File.separator + fileEntity.getId());
        Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
    }

    private String getUri(FileEntity fileEntity) {
        return UriComponentsBuilder.fromHttpUrl(serviceProperties.getHost())
                .path(EndpointConstant.FILE)
                .path(String.format("/%s", fileEntity.getId().toString()))
                .toUriString();
    }

    private FileEntity saveToRepository(MultipartFile file) {
        var fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        var extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        var fileEntity = FileEntity.builder()
                .name(fileName)
                .extension(extension)
                .contentType(file.getContentType())
                .size(file.getSize())
                .build();

        return fileRepository.save(fileEntity);
    }

}
