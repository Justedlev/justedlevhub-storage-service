package com.justedlev.storage.client;

import com.justedlev.storage.client.configuration.JstorageFeignClientConfiguration;
import com.justedlev.storage.model.response.DeletedFileResponse;
import com.justedlev.storage.model.response.UploadFileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(
        name = "jstorage-api-client",
        url = "${jstorage.client.url}",
        configuration = JstorageFeignClientConfiguration.class
)
public interface JstorageFeignClient {
    @PostMapping(value = EndpointConstant.FILE_UPLOAD, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    List<UploadFileResponse> upload(@RequestPart List<MultipartFile> files);

    @DeleteMapping(value = EndpointConstant.FILE_FILE_NAME_DELETE)
    DeletedFileResponse delete(@PathVariable String fileName);

    @GetMapping(value = EndpointConstant.FILE_FILE_NAME)
    Resource download(@PathVariable String fileName);
}
