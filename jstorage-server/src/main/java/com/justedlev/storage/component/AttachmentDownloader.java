package com.justedlev.storage.component;

import com.justedlev.storage.model.response.AttachmentResponse;

import java.util.UUID;

public interface AttachmentDownloader {
    AttachmentResponse download(UUID id);
}
