package com.justedlev.storage.component;

import com.justedlev.storage.model.response.DownloadFileResponse;

public interface DownloadFileComponent {
    DownloadFileResponse download(String fileName);
}
