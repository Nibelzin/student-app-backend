package com.studentapp.api.infra.adapters.out.persistance.mapper;

import com.studentapp.api.domain.model.FileObject;
import com.studentapp.api.infra.adapters.out.persistance.entity.FileObjectEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class FileObjectMapper {

    public abstract FileObjectEntity toEntity(FileObject fileObject);

    public FileObject toDomain(FileObjectEntity entity) {
        if (entity == null) return null;

        return FileObject.fromState(
                entity.getId(), entity.getStorageProvider(), entity.getName(),
                entity.getBucket(), entity.getStoragePath(), entity.getMimeType(),
                entity.getSize(), entity.getChecksum(), entity.getCreatedAt()
        );
    }
}
