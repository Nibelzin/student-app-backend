package com.studentapp.api.infra.adapters.in.web.mapper;

import com.studentapp.api.domain.model.FileObject;
import com.studentapp.api.infra.adapters.in.web.dto.fileObject.FileObjectResponse;
import org.springframework.stereotype.Component;

@Component
public class FileObjectDtoMapper {

    public FileObjectResponse toResponse(FileObject fileObject){
        if (fileObject == null){
            return null;
        }

        return new FileObjectResponse(
                fileObject.getId(),
                fileObject.getName(),
                fileObject.getStorageProvider(),
                fileObject.getBucket(),
                fileObject.getStoragePath(),
                fileObject.getMimeType(),
                fileObject.getSize(),
                fileObject.getChecksum(),
                fileObject.getCreatedAt()
        );
    }

}
