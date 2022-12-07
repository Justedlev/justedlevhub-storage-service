package com.justedlev.service.storage.repository;

import com.justedlev.service.storage.repository.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface FileRepository extends JpaRepository<FileEntity, UUID>, JpaSpecificationExecutor<FileEntity> {
}
