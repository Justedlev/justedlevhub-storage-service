package com.justedlev.service.storage.controller;

import com.justedlev.service.storage.constant.EndpointConstant;
import com.justedlev.service.storage.model.response.FileResponse;
import com.justedlev.service.storage.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(EndpointConstant.FILE)
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping(value = EndpointConstant.UPLOAD, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileResponse> upload(@RequestPart MultipartFile file) {
        return ResponseEntity.ok(fileService.store(file));
    }

    @SneakyThrows
    @GetMapping(value = EndpointConstant.FILE_ID)
    public ResponseEntity<Resource> preview(@PathVariable UUID fileId) {
        var file = fileService.getById(fileId);
        var bytes = Base64.getDecoder().decode(file.getData());
        var inputStream = new ByteArrayInputStream(bytes);
        var inputStreamResource = new InputStreamResource(inputStream);
        var contentDisposition = ContentDisposition.inline()
                .filename(file.getName())
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(bytes.length))
                .contentLength(bytes.length)
                .contentType(MediaType.valueOf(file.getContentType()))
                .body(inputStreamResource);
    }

    @SneakyThrows
    @GetMapping(value = EndpointConstant.DOWNLOAD_FILE_ID)
    public ResponseEntity<Resource> download(@PathVariable UUID fileId) {
        var file = fileService.getById(fileId);
        var inputStream = new FileInputStream(file.getData());
        var inputStreamResource = new InputStreamResource(inputStream);
        var contentDisposition = ContentDisposition.attachment()
                .filename(file.getName())
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.getSize()))
                .contentLength(file.getSize())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(inputStreamResource);
    }
}
