package com.studentapp.api.infra.adapters.out.persistance.mapper;

import com.studentapp.api.domain.model.ClassSchedule;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.infra.adapters.out.persistance.entity.ClassScheduleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring")
public abstract class ClassScheduleMapper {

    @Autowired
    @Lazy
    protected SubjectMapper subjectMapper;

    @Mapping(target = "subject", expression = "java(subjectMapper.toEntity(classSchedule.getSubject()))")
    public abstract ClassScheduleEntity toEntity(ClassSchedule classSchedule);

    public ClassSchedule toDomain(ClassScheduleEntity entity) {
        if (entity == null) return null;

        Subject subject = subjectMapper.toDomain(entity.getSubject());

        return ClassSchedule.fromState(
                entity.getId(),
                entity.getWeekDay(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getLocation(),
                subject
        );
    }
}
