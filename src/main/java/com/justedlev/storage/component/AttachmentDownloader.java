package com.justedlev.storage.component;

import com.justedlevhub.api.model.response.AttachmentResponse;

import java.util.UUID;

public interface AttachmentDownloader {
    AttachmentResponse download(UUID id);
}
