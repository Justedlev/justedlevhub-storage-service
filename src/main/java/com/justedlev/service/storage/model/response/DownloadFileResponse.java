package com.justedlev.service.storage.model.response;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

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
    @Builder.Default
    private MediaType contentType = MediaType.APPLICATION_OCTET_STREAM;
    @Builder.Default
    private Long size = 0L;
    private Resource resource;
    @Builder.Default
    private HttpHeaders headers = new HttpHeaders();
}
