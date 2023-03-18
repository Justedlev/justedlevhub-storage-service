package com.justedlev.storage.component;

import com.justedlev.storage.model.response.DownloadAttachmentResponse;

import java.util.UUID;

public interface AttachmentDownloader {
    DownloadAttachmentResponse download(UUID id);
}
