package com.studentapp.api.infra.adapters.in.web.dto.fileObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileObjectResponse {

    private UUID id;
    private String name;
    private String storageProvider;
    private String bucket;
    private String storagePath;
    private String mimeType;
    private Long size;
    private String checksum;

    private LocalDateTime createdAt;

}
