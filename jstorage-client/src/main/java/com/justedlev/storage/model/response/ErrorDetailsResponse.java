package com.justedlev.storage.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ErrorDetailsResponse {
    @Builder.Default
    private Date timestamp = new Date();
    private String message;
    private String details;
}
