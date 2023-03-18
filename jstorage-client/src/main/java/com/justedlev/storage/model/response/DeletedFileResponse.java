package com.justedlev.storage.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DeletedFileResponse {
    @Builder.Default
    private Boolean isDeleted = Boolean.FALSE;
    private String name;
    private String extension;
    private String contentType;
    private String url;
    @Builder.Default
    private Long size = -1L;
}
