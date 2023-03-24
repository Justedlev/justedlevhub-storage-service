package com.justedlev.storage.service;

import com.justedlev.storage.model.response.AttachmentInfoResponse;
import com.justedlev.storage.model.response.AttachmentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface AttachmentService {
    List<AttachmentInfoResponse> store(List<MultipartFile> files);

    AttachmentInfoResponse delete(UUID id);

    AttachmentResponse download(UUID id);
}
