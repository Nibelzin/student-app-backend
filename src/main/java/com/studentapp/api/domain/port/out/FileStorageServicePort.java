package com.studentapp.api.domain.port.out;

import com.studentapp.api.domain.port.in.MaterialUseCase;

public interface FileStorageServicePort {

    StorageDetails save(MaterialUseCase.FileInput fileInput, String path);

    void delete(String filePath);

    String generatePresignedUrl(String storagePath);

    record StorageDetails(
            String name,
            String provider,
            String bucket,
            String path,
            String mimeType,
            Long size,
            String checksum
    ) {}
}
