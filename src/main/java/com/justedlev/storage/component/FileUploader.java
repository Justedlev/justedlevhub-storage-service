package com.justedlev.storage.component;

import com.justedlevhub.api.model.response.AttachmentInfoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploader {
	List<AttachmentInfoResponse> upload(List<MultipartFile> files);
}
