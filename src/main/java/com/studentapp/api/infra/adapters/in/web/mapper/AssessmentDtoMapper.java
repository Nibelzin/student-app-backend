package com.studentapp.api.infra.adapters.in.web.mapper;

import com.studentapp.api.domain.model.Assessment;
import com.studentapp.api.infra.adapters.in.web.dto.assessment.AssessmentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AssessmentDtoMapper {

    @Mapping(target = "subjectId", expression = "java(assessment.getSubject().getId())")
    @Mapping(target = "subjectName", expression = "java(assessment.getSubject().getName())")
    AssessmentResponse toResponse(Assessment assessment);
}
