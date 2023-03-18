package com.justedlev.storage.repository.entity;

import com.justedlev.common.entity.BaseEntity;
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
@Table(name = "attachments")
@ToString
public class Attachment extends BaseEntity implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @Column(name = "filename", nullable = false)
    private String filename;
    @Column(name = "extension", nullable = false)
    private String extension;
    @Column(name = "content_type", nullable = false)
    private String contentType;
    @Column(name = "length", nullable = false)
    private Long length;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Attachment attachment = (Attachment) o;
        return id != null && Objects.equals(id, attachment.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
