package com.justedlev.storage.service;

import com.justedlev.storage.model.response.DeletedFileResponse;
import com.justedlev.storage.model.response.DownloadAttachmentResponse;
import com.justedlev.storage.model.response.AttachmentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface AttachmentService {
    List<AttachmentResponse> store(List<MultipartFile> files);

    DeletedFileResponse delete(UUID id);

    DownloadAttachmentResponse download(UUID id);
}
