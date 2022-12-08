package com.justedlev.service.storage.model.response;

import org.springframework.core.io.Resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DownloadFileResponse {
    private String name;
    private String extension;
    private String contentType;
    private Long size;
    private Resource resource;
}
