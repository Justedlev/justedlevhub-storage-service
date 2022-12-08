package com.justedlev.service.storage.component;

import org.springframework.web.multipart.MultipartFile;

import com.justedlev.service.storage.model.response.UploadFileResponse;

public interface UploadFileComponent {
	UploadFileResponse upload(MultipartFile file);
}
