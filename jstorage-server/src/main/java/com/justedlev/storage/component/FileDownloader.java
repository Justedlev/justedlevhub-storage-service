package com.justedlev.storage.component;

import com.justedlev.storage.model.response.DownloadFileResponse;

public interface FileDownloader {
    DownloadFileResponse download(String fileName);
}
