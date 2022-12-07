package com.justedlev.service.storage.repository.entity;

import com.justedlev.service.storage.repository.entity.base.BaseEntity;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "files")
@ToString
public class FileEntity extends BaseEntity implements Serializable {
    @Id
    @Column(name = "file_id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @Column(name = "file_name", nullable = false)
    private String name;
    @Column(name = "file_extension", nullable = false)
    private String extension;
    @Column(name = "content_type", nullable = false)
    private String contentType;
    @Column(name = "path", columnDefinition = "text")
    private String path;
    @Column(name = "size", nullable = false)
    private Long size;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FileEntity fileEntity = (FileEntity) o;
        return id != null && Objects.equals(id, fileEntity.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
