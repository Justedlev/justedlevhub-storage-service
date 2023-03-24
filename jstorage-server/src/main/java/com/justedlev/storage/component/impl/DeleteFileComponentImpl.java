package com.justedlev.storage.component.impl;

import com.justedlev.storage.component.DeleteFileComponent;
import com.justedlev.storage.model.response.AttachmentInfoResponse;
import com.justedlev.storage.properties.JStorageProperties;
import com.justedlev.storage.repository.AttachmentRepository;
import com.justedlev.storage.repository.entity.Attachment;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteFileComponentImpl implements DeleteFileComponent {
    private final AttachmentRepository attachmentRepository;
    private final JStorageProperties properties;
    private final ModelMapper defaultMapper;

    @Override
    public AttachmentInfoResponse delete(UUID id) {
        return attachmentRepository.findById(id)
                .map(current -> defaultMapper.map(current, AttachmentInfoResponse.class))
                .orElse(null);

    }

    @SneakyThrows
    private Boolean delete(Attachment entity) {
        var file = properties.getRootPath().resolve(entity.getId().toString());
        if (!Files.exists(file)) return Boolean.FALSE;
        Files.delete(file);
        attachmentRepository.delete(entity);

        return Boolean.TRUE;
    }
}
