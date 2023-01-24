package com.justedlev.storage.controller;

import com.justedlev.storage.client.EndpointConstant;
import com.justedlev.storage.common.validator.NotEmptyMultipartFile;
import com.justedlev.storage.model.response.DeletedFileResponse;
import com.justedlev.storage.model.response.FileResponse;
import com.justedlev.storage.service.FileService;
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

@Slf4j
@RestController
@RequestMapping(EndpointConstant.FILE)
@RequiredArgsConstructor
@Validated
public class FileController {
    private final FileService fileService;

    @PostMapping(value = EndpointConstant.UPLOAD, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<FileResponse>> upload(@RequestPart @Valid List<@NotEmptyMultipartFile MultipartFile> files) {
        return ResponseEntity.ok(fileService.store(files));
    }

    @DeleteMapping(value = EndpointConstant.FILE_NAME_DELETE)
    public ResponseEntity<DeletedFileResponse> delete(@PathVariable String fileName) {
        return ResponseEntity.ok(fileService.delete(fileName));
    }

    @GetMapping(value = EndpointConstant.FILE_NAME)
    public ResponseEntity<Resource> download(@PathVariable String fileName) {
        var file = fileService.getByName(fileName);

        return ResponseEntity.ok()
                .headers(file.getHeaders())
                .contentLength(file.getSize())
                .contentType(file.getContentType())
                .body(file.getResource());
    }
}
