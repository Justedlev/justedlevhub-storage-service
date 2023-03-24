package com.justedlev.storage.component;

import com.justedlev.storage.model.response.AttachmentInfoResponse;

import java.util.UUID;

public interface DeleteFileComponent {
    AttachmentInfoResponse delete(UUID id);
}
