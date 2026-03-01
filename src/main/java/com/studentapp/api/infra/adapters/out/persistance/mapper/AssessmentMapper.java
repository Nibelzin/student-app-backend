package com.studentapp.api.infra.adapters.out.persistance.mapper;

import com.studentapp.api.domain.model.Assessment;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.infra.adapters.out.persistance.entity.AssessmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring")
public abstract class AssessmentMapper {

    @Autowired
    @Lazy
    protected SubjectMapper subjectMapper;

    @Mapping(target = "subject", expression = "java(subjectMapper.toEntity(assessment.getSubject()))")
    public abstract AssessmentEntity toEntity(Assessment assessment);

    public Assessment toDomain(AssessmentEntity entity) {
        if (entity == null) return null;

        Subject subject = subjectMapper.toDomain(entity.getSubject());

        return Assessment.fromState(
                entity.getId(),
                entity.getTitle(),
                entity.getAssessmentDate(),
                entity.getGrade(),
                entity.getMaxGrade(),
                entity.getWeight(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                subject
        );
    }
}
