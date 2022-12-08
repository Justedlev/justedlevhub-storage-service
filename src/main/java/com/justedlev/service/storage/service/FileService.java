package com.justedlev.service.storage.service;

import com.justedlev.service.storage.model.response.DownloadFileResponse;
import com.justedlev.service.storage.model.response.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    List<FileResponse> store(List<MultipartFile> files);

    Boolean delete(String fileName);

    DownloadFileResponse getByName(String fileName);
}
