package com.studentapp.api.infra.adapters.in.web.mapper;

import com.studentapp.api.domain.model.AbsenceLog;
import com.studentapp.api.infra.adapters.in.web.dto.absenceLog.AbsenceLogResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AbsenceLogDtoMapper {

    @Mapping(target = "subjectId", expression = "java(absenceLog.getSubject().getId())")
    @Mapping(target = "subjectName", expression = "java(absenceLog.getSubject().getName())")
    AbsenceLogResponse toResponse(AbsenceLog absenceLog);

}