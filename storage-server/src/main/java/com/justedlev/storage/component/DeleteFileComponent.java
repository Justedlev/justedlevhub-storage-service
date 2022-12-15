package com.justedlev.storage.component;

import com.justedlev.storage.model.response.DeletedFileResponse;

public interface DeleteFileComponent {
    DeletedFileResponse deleteByName(String fileName);
}
