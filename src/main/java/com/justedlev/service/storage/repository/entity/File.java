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
public class File extends BaseEntity implements Serializable {
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
    @Lob
    @Column(name = "data", columnDefinition = "text", nullable = false)
    private byte[] data;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        File file = (File) o;
        return id != null && Objects.equals(id, file.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
