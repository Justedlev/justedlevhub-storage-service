package com.justedlev.storage.component.impl;

import com.justedlev.storage.component.AttachmentDownloader;
import com.justedlev.storage.model.response.DownloadAttachmentResponse;
import com.justedlev.storage.properties.JStorageProperties;
import com.justedlev.storage.repository.AttachmentRepository;
import com.justedlev.storage.repository.entity.Attachment;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.nio.file.Files;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AttachmentDownloaderImpl implements AttachmentDownloader {
    private final JStorageProperties properties;
    private final AttachmentRepository attachmentRepository;

    @Override
    public DownloadAttachmentResponse download(UUID id) {
        Attachment entity = attachmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("File %s not found", id)));
        var file = getFile(entity);

        return DownloadAttachmentResponse.builder()
                .filename(entity.getFilename())
                .extension(entity.getExtension())
                .resource(new FileSystemResource(file))
                .length(file.length())
                .contentType(MediaType.parseMediaType(entity.getContentType()))
                .build();
    }

    private File getFile(Attachment attachment) {
        var path = properties.getRootPath().resolve(attachment.getId().toString());

        if (!Files.exists(path)) {
            attachmentRepository.delete(attachment);
            throw new EntityNotFoundException(String.format("File %s not found", attachment.getId()));
        }

        return path.toFile();
    }
}
