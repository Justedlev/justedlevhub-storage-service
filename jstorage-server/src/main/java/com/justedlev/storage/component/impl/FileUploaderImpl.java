package com.justedlev.storage.component.impl;

import com.justedlev.storage.component.FileUploader;
import com.justedlev.storage.model.response.AttachmentInfoResponse;
import com.justedlev.storage.properties.JStorageProperties;
import com.justedlev.storage.properties.ServiceProperties;
import com.justedlev.storage.repository.AttachmentRepository;
import com.justedlev.storage.repository.entity.Attachment;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FileUploaderImpl implements FileUploader {
    private final JStorageProperties properties;
    private final ServiceProperties serviceProperties;
    private final AttachmentRepository attachmentRepository;

    @SneakyThrows
    @Transactional
    @Override
    public List<AttachmentInfoResponse> upload(List<MultipartFile> files) {
        return saveFiles(files).stream()
                .map(current -> AttachmentInfoResponse.builder()
                        .url(getUri(current))
                        .contentType(current.getContentType())
                        .extension(current.getExtension())
                        .filename(current.getFilename())
                        .length(current.getLength())
                        .build())
                .toList();
    }

    private String getUri(Attachment attachment) {
        return UriComponentsBuilder.fromHttpUrl(serviceProperties.getHost())
                .path("/file")
                .path("/" + attachment.getId())
                .path("/" + attachment.getFilename())
                .toUriString();
    }

    private List<Attachment> saveFiles(List<MultipartFile> files) {
        return files.stream()
                .map(this::saveFile)
                .toList();
    }

    @SneakyThrows
    private Attachment saveFile(MultipartFile multipartFile) {
        var attachment = attachmentRepository.save(toEntity(multipartFile));
        var copyLocation = properties.getRootPath().resolve(attachment.getId().toString());
        Files.copy(multipartFile.getInputStream(), copyLocation);

        return attachment;
    }

    private Attachment toEntity(MultipartFile file) {
        var fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        var extension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        return Attachment.builder()
                .filename(fileName)
                .extension(extension)
                .contentType(file.getContentType())
                .length(file.getSize())
                .build();
    }
}
