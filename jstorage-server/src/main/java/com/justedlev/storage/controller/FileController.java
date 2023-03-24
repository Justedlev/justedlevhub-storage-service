package com.justedlev.storage.controller;

import com.justedlev.storage.model.response.AttachmentInfoResponse;
import com.justedlev.storage.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@Validated
public class FileController {
    private final AttachmentService attachmentService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<AttachmentInfoResponse>> upload(@RequestPart List<MultipartFile> files) {
        return ResponseEntity.ok(attachmentService.store(files));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<AttachmentInfoResponse> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(attachmentService.delete(id));
    }

    @GetMapping(value = "/{id}/{filename}")
    public ResponseEntity<Resource> download(@PathVariable UUID id, @PathVariable String filename) {
        log.info("Request to download file: {}", filename);
        return attachmentService.download(id).buildResponse();
    }
}
