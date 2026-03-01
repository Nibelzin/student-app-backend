package com.studentapp.api.infra.adapters.in.web.mapper;

import com.studentapp.api.domain.model.ClassSchedule;
import com.studentapp.api.infra.adapters.in.web.dto.classSchedule.ClassScheduleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClassScheduleDtoMapper {

    @Mapping(target = "subjectId", expression = "java(classSchedule.getSubject().getId())")
    @Mapping(target = "subjectName", expression = "java(classSchedule.getSubject().getName())")
    ClassScheduleResponse toResponse(ClassSchedule classSchedule);
}
