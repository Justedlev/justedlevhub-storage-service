package com.justedlev.service.storage.component;

import com.justedlev.service.storage.model.response.DownloadFileResponse;

public interface DownloadFileComponent {
    DownloadFileResponse download(String fileName);
}
