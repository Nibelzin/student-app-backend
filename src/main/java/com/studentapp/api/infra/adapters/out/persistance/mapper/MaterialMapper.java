package com.studentapp.api.infra.adapters.out.persistance.mapper;

import com.studentapp.api.domain.model.FileObject;
import com.studentapp.api.domain.model.Material;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.infra.adapters.out.persistance.entity.FileObjectEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.MaterialEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.SubjectEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class MaterialMapper {

    private final FileObjectMapper fileObjectMapper;
    private final SubjectMapper subjectMapper;

    public MaterialMapper(@Lazy FileObjectMapper fileObjectMapper, @Lazy SubjectMapper subjectMapper) {
        this.fileObjectMapper = fileObjectMapper;
        this.subjectMapper = subjectMapper;
    }

    public MaterialEntity toEntity(Material material) {
        if(material == null){
            return null;
        }

        SubjectEntity subjectEntity = subjectMapper.toEntity(material.getSubject());

        FileObjectEntity fileObjectEntity = null;
        if(material.getFile() != null){
            FileObject fileObject = material.getFile();

            fileObjectEntity = fileObjectMapper.toEntity(fileObject);
        }

        return new MaterialEntity(material.getId(), material.getTitle(), material.getType(), material.getExternalUrl(), material.getFavorite(), material.getCreatedAt(), material.getUpdatedAt(), subjectEntity, fileObjectEntity);
    }

    public Material toDomain(MaterialEntity entity){
        if(entity == null){
            return null;
        }

        Subject subjectDomain = subjectMapper.toDomain(entity.getSubject());

        FileObject fileObjectDomain = null;
        if(entity.getFile() != null){
            FileObjectEntity fileObjectEntity = entity.getFile();

            fileObjectDomain = fileObjectMapper.toDomain(fileObjectEntity);
        }

        return Material.fromState(entity.getId(), entity.getTitle(), entity.getType(), entity.getExternalUrl(), entity.getIsFavorite(), entity.getCreatedAt(), entity.getUpdatedAt(), subjectDomain, fileObjectDomain);
    }

}
