package com.justedlev.storage.service.impl;

import com.justedlev.storage.component.DeleteFileComponent;
import com.justedlev.storage.component.AttachmentDownloader;
import com.justedlev.storage.component.FileUploader;
import com.justedlev.storage.model.response.DeletedFileResponse;
import com.justedlev.storage.model.response.DownloadAttachmentResponse;
import com.justedlev.storage.model.response.AttachmentResponse;
import com.justedlev.storage.service.AttachmentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final ModelMapper defaultMapper;
    private final FileUploader fileUploader;
    private final AttachmentDownloader attachmentDownloader;
    private final DeleteFileComponent deleteFileComponent;

    @Override
    public List<AttachmentResponse> store(@NonNull List<MultipartFile> files) {
        var totalSize = files.stream()
                .mapToLong(MultipartFile::getSize)
                .sum();
        var res = fileUploader.upload(files);
        log.info("Uploaded {} files with total size : {} bites", res.size(), totalSize);

        return List.of(defaultMapper.map(res, AttachmentResponse[].class));
    }

    @Override
    public DeletedFileResponse delete(UUID id) {
        return deleteFileComponent.delete(id);
    }

    public DownloadAttachmentResponse download(UUID id) {
        return attachmentDownloader.download(id);
    }
}
