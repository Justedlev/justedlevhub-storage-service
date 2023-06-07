package com.justedlev.storage.component;

import com.justedlevhub.api.model.response.AttachmentInfoResponse;

import java.util.UUID;

public interface DeleteFileComponent {
    AttachmentInfoResponse delete(UUID id);
}
