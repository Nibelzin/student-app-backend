package com.studentapp.api.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class File {

    private final UUID id;
    private final String storageProvider;
    private final String bucket;
    private final String storagePath;
    private final String mimeType;
    private final Integer size;
    private final String checksum;

    private final LocalDateTime createdAt;

    private File(UUID id, String storageProvider, String bucket, String storagePath, String mimeType, Integer size, String checksum, LocalDateTime createdAt) {
        this.id = id;
        this.storageProvider = storageProvider;
        this.bucket = bucket;
        this.storagePath = storagePath;
        this.mimeType = mimeType;
        this.size = size;
        this.checksum = checksum;
        this.createdAt = createdAt;
    }

    private File(String storageProvider, String bucket, String storagePath, String mimeType, Integer size, String checksum) {
        this.id = UUID.randomUUID();
        this.storageProvider = storageProvider;
        this.bucket = bucket;
        this.storagePath = storagePath;
        this.mimeType = mimeType;
        this.size = size;
        this.checksum = checksum;
        this.createdAt = LocalDateTime.now();
    }

    public static File create(String storageProvider, String bucket, String storagePath, String mimeType, Integer size, String checksum) {
        return new File(storageProvider, bucket, storagePath, mimeType, size, checksum);
    }

    public static File fromState(UUID id, String storageProvider, String bucket, String storagePath, String mimeType, Integer size, String checksum, LocalDateTime createdAt) {
        return new File(id, storageProvider, bucket, storagePath, mimeType, size, checksum, createdAt);
    }

    public UUID getId() {
        return id;
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

    public Integer getSize() {
        return size;
    }

    public String getChecksum() {
        return checksum;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
