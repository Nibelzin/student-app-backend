package com.studentapp.api.infra.adapters.out.persistance.mapper;

import com.studentapp.api.domain.model.FileObject;
import com.studentapp.api.infra.adapters.out.persistance.entity.FileObjectEntity;
import org.springframework.stereotype.Component;

@Component
public class FileObjectMapper {

    public FileObjectEntity toEntity(FileObject fileObject){
        if (fileObject == null) {
            return null;
        }

        return new FileObjectEntity(
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

    public FileObject toDomain(FileObjectEntity entity){
        if (entity == null) {
            return null;
        };

        return FileObject.fromState(entity.getId(), entity.getStorageProvider(), entity.getName(), entity.getBucket(), entity.getStoragePath(), entity.getMimeType(), entity.getSize(), entity.getChecksum(), entity.getCreatedAt());
    }

}
