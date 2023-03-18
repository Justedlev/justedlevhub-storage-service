package com.justedlev.storage.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DownloadAttachmentResponse {
    private String filename;
    private String extension;
    @Builder.Default
    private MediaType contentType = MediaType.APPLICATION_OCTET_STREAM;
    @Builder.Default
    private Long length = 0L;
    private Resource resource;
    @Builder.Default
    private HttpHeaders headers = new HttpHeaders();

    public ResponseEntity<Resource> buildResponse() {
        getHeaders().add(HttpHeaders.CONTENT_DISPOSITION, getContentDisposition());

        return ResponseEntity.ok()
                .headers(getHeaders())
                .contentLength(getLength())
                .contentType(getContentType())
                .body(getResource());
    }

    private String getContentDisposition() {
        if (StringUtils.containsAnyIgnoreCase(getContentType().getType(), "video", "image", "audio")) {
            return String.format("inline; \"%s\"", getFilename());
        }

        return String.format("attachment; \"%s\"", getFilename());
    }
}
