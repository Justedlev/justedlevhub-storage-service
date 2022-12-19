package com.justedlev.storage.client;

import com.justedlev.storage.client.configuration.StorageFeignClientConfiguration;
import com.justedlev.storage.model.response.DeletedFileResponse;
import com.justedlev.storage.model.response.UploadFileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(
        name = "storage-api-client",
        url = "${justedlev-service.storage.client.url}",
        configuration = StorageFeignClientConfiguration.class
)
public interface StorageFeignClient {
    @PostMapping(value = "/v1/file/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    List<UploadFileResponse> upload(@RequestPart List<MultipartFile> files);

    @DeleteMapping(value = "/v1/file/{fileName}/delete")
    DeletedFileResponse delete(@PathVariable String fileName);

    @GetMapping(value = "/v1/file/{fileName}")
    Resource download(@PathVariable String fileName);
}
