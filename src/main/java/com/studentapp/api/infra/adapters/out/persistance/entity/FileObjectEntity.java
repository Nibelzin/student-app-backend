package com.studentapp.api.infra.adapters.out.persistance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "file_object")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileObjectEntity {

    @Id
    private UUID id;

    @Column
    private String name;

    @Column
    private String storageProvider;

    @Column
    private String bucket;

    @Column(name = "storage_path")
    private String storagePath;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "size_bytes")
    private Long size;

    @Column
    private String checksum;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

}
