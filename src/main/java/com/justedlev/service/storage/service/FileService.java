package com.justedlev.service.storage.service;

import com.justedlev.service.storage.model.response.DownloadFileResponse;
import com.justedlev.service.storage.model.response.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileService {
    FileResponse store(MultipartFile file);

    DownloadFileResponse getById(UUID id);
}
