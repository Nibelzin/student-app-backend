package com.studentapp.api.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class FileObject {

    private final UUID id;
    private final String name;
    private final String storageProvider;
    private final String bucket;
    private final String storagePath;
    private final String mimeType;
    private final Long size;
    private final String checksum;

    private final LocalDateTime createdAt;

    private FileObject(UUID id, String name, String storageProvider, String bucket, String storagePath, String mimeType, Long size, String checksum, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.storageProvider = storageProvider;
        this.bucket = bucket;
        this.storagePath = storagePath;
        this.mimeType = mimeType;
        this.size = size;
        this.checksum = checksum;
        this.createdAt = createdAt;
    }

    private FileObject(String name, String storageProvider, String bucket, String storagePath, String mimeType, Long size, String checksum) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.storageProvider = storageProvider;
        this.bucket = bucket;
        this.storagePath = storagePath;
        this.mimeType = mimeType;
        this.size = size;
        this.checksum = checksum;
        this.createdAt = LocalDateTime.now();
    }

    public static FileObject create(String name, String storageProvider, String bucket, String storagePath, String mimeType, Long size, String checksum) {
        return new FileObject(name, storageProvider, bucket, storagePath, mimeType, size, checksum);
    }

    public static FileObject fromState(UUID id, String storageProvider, String name, String bucket, String storagePath, String mimeType, Long size, String checksum, LocalDateTime createdAt) {
        return new FileObject(id, name, storageProvider, bucket, storagePath, mimeType, size, checksum, createdAt);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStorageProvider() {
        return storageProvider;
    }

    public String getBucket() {
        return bucket;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public String getMimeType() {
        return mimeType;
    }

    public Long getSize() {
        return size;
    }

    public String getChecksum() {
        return checksum;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
