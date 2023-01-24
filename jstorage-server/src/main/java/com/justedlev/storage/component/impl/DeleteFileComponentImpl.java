package com.justedlev.storage.component.impl;

import com.justedlev.storage.component.DeleteFileComponent;
import com.justedlev.storage.model.response.DeletedFileResponse;
import com.justedlev.storage.properties.JStorageProperties;
import com.justedlev.storage.repository.FileRepository;
import com.justedlev.storage.repository.entity.FileEntity;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.nio.file.Files;

@Component
@RequiredArgsConstructor
public class DeleteFileComponentImpl implements DeleteFileComponent {
    private final FileRepository fileRepository;
    private final JStorageProperties properties;
    private final ModelMapper defaultMapper;

    @Override
    public DeletedFileResponse deleteByName(String fileName) {
        return fileRepository.findByFileName(fileName)
                .map(current -> {
                    var res = defaultMapper.map(current, DeletedFileResponse.class);
                    res.setIsDeleted(delete(current));

                    return res;
                })
                .orElse(DeletedFileResponse.builder()
                        .fileName(fileName)
                        .build());

    }

    @SneakyThrows
    private Boolean delete(FileEntity entity) {
        var file = properties.getRootPath().resolve(entity.getFileName());

        if (!Files.exists(file)) {
            return Boolean.FALSE;
        }

        Files.delete(file);
        fileRepository.delete(entity);

        return Boolean.TRUE;
    }
}
