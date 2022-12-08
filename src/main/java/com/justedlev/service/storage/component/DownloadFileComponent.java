package com.justedlev.service.storage.component;

import java.util.UUID;

import com.justedlev.service.storage.model.response.DownloadFileResponse;

public interface DownloadFileComponent {
    DownloadFileResponse download(UUID fileId);
}
