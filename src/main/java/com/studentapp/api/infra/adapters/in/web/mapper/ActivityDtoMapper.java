package com.studentapp.api.infra.adapters.in.web.mapper;

import com.studentapp.api.domain.model.Activity;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.infra.adapters.in.web.dto.activity.ActivityCreateRequest;
import com.studentapp.api.infra.adapters.in.web.dto.activity.ActivityResponse;
import org.springframework.stereotype.Component;

@Component
public class ActivityDtoMapper {

    public Activity toDomain(ActivityCreateRequest activityCreateRequest, Subject subject) {
        return Activity.create(
                activityCreateRequest.getTitle(),
                activityCreateRequest.getDescription(),
                activityCreateRequest.getDueDate(),
                activityCreateRequest.getIsCompleted(),
                activityCreateRequest.getType(),
                subject
        );
    }

    public ActivityResponse toResponse(Activity activity) {
        if (activity == null) {
            return null;
        }

        ActivityResponse response = new ActivityResponse();
        response.setId(activity.getId());
        response.setTitle(activity.getTitle());
        response.setDescription(activity.getDescription());
        response.setDueDate(activity.getDueDate());
        response.setIsCompleted(activity.getCompleted());
        response.setType(activity.getType());
        response.setCreatedAt(activity.getCreatedAt());
        response.setUpdatedAt(activity.getUpdatedAt());
        response.setSubjectId(activity.getSubject().getId());
        response.setSubjectName(activity.getSubject().getName());
        return response;
    }

}
