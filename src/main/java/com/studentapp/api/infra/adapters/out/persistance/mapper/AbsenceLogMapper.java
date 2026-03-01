package com.studentapp.api.infra.adapters.out.persistance.mapper;

import com.studentapp.api.domain.model.AbsenceLog;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.infra.adapters.out.persistance.entity.AbsenceLogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring")
public abstract class AbsenceLogMapper {

    @Autowired
    @Lazy
    protected SubjectMapper subjectMapper;

    @Mapping(target = "subject", expression = "java(subjectMapper.toEntity(absenceLog.getSubject()))")
    public abstract AbsenceLogEntity toEntity(AbsenceLog absenceLog);

    public AbsenceLog toDomain(AbsenceLogEntity entity) {
        if (entity == null) return null;

        Subject subject = subjectMapper.toDomain(entity.getSubject());

        return AbsenceLog.fromState(
                entity.getId(),
                entity.getAbsenceDate(),
                entity.getNotes(),
                entity.getCreatedAt(),
                subject
        );
    }
}
