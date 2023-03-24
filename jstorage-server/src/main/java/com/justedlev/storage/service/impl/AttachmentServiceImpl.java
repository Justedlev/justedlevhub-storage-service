package com.justedlev.storage.service.impl;

import com.justedlev.storage.component.AttachmentDownloader;
import com.justedlev.storage.component.DeleteFileComponent;
import com.justedlev.storage.component.FileUploader;
import com.justedlev.storage.model.response.AttachmentInfoResponse;
import com.justedlev.storage.model.response.AttachmentResponse;
import com.justedlev.storage.service.AttachmentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final FileUploader fileUploader;
    private final AttachmentDownloader attachmentDownloader;
    private final DeleteFileComponent deleteFileComponent;

    @Override
    public List<AttachmentInfoResponse> store(@NonNull List<MultipartFile> files) {
        return fileUploader.upload(files);
    }

    @Override
    public AttachmentInfoResponse delete(UUID id) {
        return deleteFileComponent.delete(id);
    }

    public AttachmentResponse download(UUID id) {
        return attachmentDownloader.download(id);
    }
}
