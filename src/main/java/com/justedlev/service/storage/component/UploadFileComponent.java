package com.justedlev.service.storage.component;

import org.springframework.web.multipart.MultipartFile;

import com.justedlev.service.storage.model.response.UploadFileResponse;

import java.util.List;

public interface UploadFileComponent {
	List<UploadFileResponse> upload(List<MultipartFile> files);
}
