package com.studentapp.api.infra.adapters.in.web.mapper;

import com.studentapp.api.domain.model.Activity;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.infra.adapters.in.web.dto.activity.ActivityCreateRequest;
import com.studentapp.api.infra.adapters.in.web.dto.activity.ActivityResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ActivityDtoMapper {

    public Activity toDomain(ActivityCreateRequest request, Subject subject) {
        return Activity.create(
                request.getTitle(),
                request.getDescription(),
                request.getDueDate(),
                request.getIsCompleted(),
                request.getType(),
                subject,
                request.getChecklist()
        );
    }

    @Mapping(target = "isCompleted", source = "completed")
    @Mapping(target = "subjectId", expression = "java(activity.getSubject().getId())")
    @Mapping(target = "subjectName", expression = "java(activity.getSubject().getName())")
    public abstract ActivityResponse toResponse(Activity activity);
}
