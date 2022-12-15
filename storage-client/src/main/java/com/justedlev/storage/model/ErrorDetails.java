package com.justedlev.storage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ErrorDetails {
    @Builder.Default
    private Date timestamp = new Date();
    private String message;
    private String details;
}
