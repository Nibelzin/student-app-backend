package com.studentapp.api.infra.adapters.out.persistance.mapper;

import com.studentapp.api.domain.model.Activity;
import com.studentapp.api.domain.model.FileObject;
import com.studentapp.api.domain.model.Material;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.infra.adapters.out.persistance.entity.MaterialEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring")
public abstract class MaterialMapper {

    @Autowired
    @Lazy
    protected FileObjectMapper fileObjectMapper;

    @Autowired
    @Lazy
    protected SubjectMapper subjectMapper;

    @Autowired
    @Lazy
    protected ActivityMapper activityMapper;

    @Mapping(target = "isFavorite", source = "favorite")
    @Mapping(target = "subject", expression = "java(subjectMapper.toEntity(material.getSubject()))")
    @Mapping(target = "activity", expression = "java(activityMapper.toEntity(material.getActivity()))")
    @Mapping(target = "file", expression = "java(fileObjectMapper.toEntity(material.getFile()))")
    public abstract MaterialEntity toEntity(Material material);

    public Material toDomain(MaterialEntity entity) {
        if (entity == null) return null;

        Subject subjectDomain = subjectMapper.toDomain(entity.getSubject());
        Activity activityDomain = activityMapper.toDomain(entity.getActivity());

        FileObject fileObjectDomain = null;
        if (entity.getFile() != null) {
            fileObjectDomain = fileObjectMapper.toDomain(entity.getFile());
        }

        return Material.fromState(
                entity.getId(), entity.getTitle(), entity.getType(), entity.getExternalUrl(),
                entity.getIsFavorite(), entity.getCreatedAt(), entity.getUpdatedAt(),
                subjectDomain, activityDomain, fileObjectDomain
        );
    }
}
