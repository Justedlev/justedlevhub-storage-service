package com.justedlev.storage.service.impl;

import com.justedlev.storage.component.DeleteFileComponent;
import com.justedlev.storage.component.DownloadFileComponent;
import com.justedlev.storage.component.UploadFileComponent;
import com.justedlev.storage.model.response.DownloadFileResponse;
import com.justedlev.storage.model.response.FileResponse;
import com.justedlev.storage.service.FileService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final ModelMapper defaultMapper;
    private final UploadFileComponent uploadFileComponent;
    private final DownloadFileComponent downloadFileComponent;
    private final DeleteFileComponent deleteFileComponent;

    @Override
    public List<FileResponse> store(@NonNull List<MultipartFile> files) {
        var totalSize = files.stream()
                .mapToLong(MultipartFile::getSize)
                .sum();
        var res = uploadFileComponent.upload(files);
        log.info("Uploaded {} files with total size : {} bites", res.size(), totalSize);

        return List.of(defaultMapper.map(res, FileResponse[].class));
    }

    @Override
    public Boolean delete(String fileName) {
        return deleteFileComponent.deleteByName(fileName);
    }

    @Override
    public DownloadFileResponse getByName(String fileName) {
        return downloadFileComponent.download(fileName);
    }
}
