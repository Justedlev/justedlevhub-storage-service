package com.justedlev.storage.service;

import com.justedlev.storage.model.response.DeletedFileResponse;
import com.justedlev.storage.model.response.DownloadFileResponse;
import com.justedlev.storage.model.response.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    List<FileResponse> store(List<MultipartFile> files);

    DeletedFileResponse delete(String fileName);

    DownloadFileResponse getByName(String fileName);
}
