package com.justedlev.service.storage.service.impl;

import com.justedlev.service.storage.component.DownloadFileComponent;
import com.justedlev.service.storage.component.UploadFileComponent;
import com.justedlev.service.storage.model.response.DownloadFileResponse;
import com.justedlev.service.storage.model.response.FileResponse;
import com.justedlev.service.storage.service.FileService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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

    @Override
    @SneakyThrows
    public List<FileResponse> store(@NonNull List<MultipartFile> files) {
        var res = uploadFileComponent.upload(files);

        return List.of(defaultMapper.map(res, FileResponse[].class));
    }

    @Override
    public DownloadFileResponse getByName(String fileName) {
        return downloadFileComponent.download(fileName);
    }
}
