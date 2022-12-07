package com.justedlev.service.storage.service.impl;

import com.justedlev.service.storage.config.properties.JStorageProperties;
import com.justedlev.service.storage.config.properties.ServiceProperties;
import com.justedlev.service.storage.constant.EndpointConstant;
import com.justedlev.service.storage.model.response.FileResponse;
import com.justedlev.service.storage.repository.FileRepository;
import com.justedlev.service.storage.repository.entity.FileEntity;
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
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final ServiceProperties serviceProperties;
    private final ModelMapper defaultMapper;
    private final JStorageProperties properties;

    @Override
    @SneakyThrows
    public FileResponse store(@NonNull MultipartFile file) {
        var fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        var extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        var fileEntity = FileEntity.builder()
                .name(fileName)
                .extension(extension)
                .contentType(file.getContentType())
                .size(file.getSize())
                .build();
        var res = fileRepository.save(fileEntity);
        Path copyLocation = Paths.get(properties.getRootPath() + File.separator + res.getId());
        Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        res.setPath(copyLocation.toString());
        res = fileRepository.save(res);
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
        FileEntity entity = fileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("File %s not found", id)));

        return defaultMapper.map(entity, FileResponse.class);
    }
}
