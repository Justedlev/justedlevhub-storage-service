package com.justedlev.storage.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AttachmentInfoResponse {
    private String filename;
    private String extension;
    private String contentType;
    private String url;
    private Long length;
}
