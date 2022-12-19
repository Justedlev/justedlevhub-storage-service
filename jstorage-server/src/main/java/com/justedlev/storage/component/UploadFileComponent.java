package com.justedlev.storage.component;

import org.springframework.web.multipart.MultipartFile;

import com.justedlev.storage.model.response.UploadFileResponse;

import java.util.List;

public interface UploadFileComponent {
	List<UploadFileResponse> upload(List<MultipartFile> files);
}
