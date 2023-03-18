package com.justedlev.storage.controller;

import com.justedlev.storage.client.EndpointConstant;
import com.justedlev.storage.model.response.AttachmentResponse;
import com.justedlev.storage.model.response.DeletedFileResponse;
import com.justedlev.storage.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(EndpointConstant.FILE)
@RequiredArgsConstructor
@Validated
public class FileController {
    private final AttachmentService attachmentService;

    @PostMapping(value = EndpointConstant.UPLOAD, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<AttachmentResponse>> upload(@RequestPart @Valid List<MultipartFile> files) {
        return ResponseEntity.ok(attachmentService.store(files));
    }

    @DeleteMapping(value = EndpointConstant.FILE_NAME_DELETE)
    public ResponseEntity<DeletedFileResponse> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(attachmentService.delete(id));
    }

    @GetMapping(value = EndpointConstant.FILE_NAME + "/*")
    public ResponseEntity<Resource> download(@PathVariable UUID id) {
        return attachmentService.download(id).buildResponse();
    }
}
