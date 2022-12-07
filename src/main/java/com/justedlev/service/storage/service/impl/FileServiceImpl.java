package com.justedlev.service.storage.service.impl;

import com.justedlev.service.storage.config.properties.ServiceProperties;
import com.justedlev.service.storage.constant.EndpointConstant;
import com.justedlev.service.storage.model.response.FileResponse;
import com.justedlev.service.storage.repository.FileRepository;
import com.justedlev.service.storage.repository.entity.File;
import com.justedlev.service.storage.service.FileService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final ServiceProperties serviceProperties;
    private final ModelMapper defaultMapper;

    @Override
    @SneakyThrows
    public FileResponse store(@NonNull MultipartFile file) {
        var fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        var extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        var content = Base64.getEncoder().encodeToString(file.getBytes());

        var fileEntity = File.builder()
                .name(fileName)
                .extension(extension)
                .content(content)
                .contentType(file.getContentType())
                .size(file.getSize())
                .build();
        var res = fileRepository.save(fileEntity);
        var fileUrl = UriComponentsBuilder.fromHttpUrl(serviceProperties.getHost())
                .path(EndpointConstant.FILE)
                .path(String.format("/%s", res.getId().toString()))
                .toUriString();

        return FileResponse.builder()
                .url(fileUrl)
                .contentType(file.getContentType())
                .extension(extension)
                .name(fileName)
                .build();
    }

    @Override
    public FileResponse getById(UUID id) {
        File entity = fileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("File %s not found", id)));

        return defaultMapper.map(entity, FileResponse.class);
    }
}
