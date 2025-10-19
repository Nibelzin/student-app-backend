package com.studentapp.api.domain.port.out;

import com.studentapp.api.domain.port.in.MaterialUseCase;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageServicePort {

    StorageDetails save(MaterialUseCase.FileInput fileInput);

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
