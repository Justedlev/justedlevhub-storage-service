package com.justedlev.storage.component;

import com.justedlev.storage.model.response.DeletedFileResponse;

import java.util.UUID;

public interface DeleteFileComponent {
    DeletedFileResponse delete(UUID id);
}
