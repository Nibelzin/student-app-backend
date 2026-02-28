package com.studentapp.api.infra.adapters.in.web.mapper;

import com.studentapp.api.domain.model.Activity;
import com.studentapp.api.domain.model.PlannerEvent;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.infra.adapters.in.web.dto.plannerEvent.PlannerEventCreateRequest;
import com.studentapp.api.infra.adapters.in.web.dto.plannerEvent.PlannerEventResponseSummary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class PlannerEventDtoMapper {

    public PlannerEvent toDomain(PlannerEventCreateRequest request, User user, Subject subject, Activity activity) {
        return PlannerEvent.create(
                request.getTitle(),
                request.getStartAt(),
                request.getEndAt(),
                request.getAllDay(),
                request.getRule(),
                request.getColor(),
                user,
                subject,
                activity
        );
    }

    @Mapping(target = "userId", expression = "java(plannerEvent.getUser().getId())")
    @Mapping(target = "userName", expression = "java(plannerEvent.getUser().getName())")
    @Mapping(target = "subjectId", expression = "java(plannerEvent.getSubject() != null ? plannerEvent.getSubject().getId() : null)")
    @Mapping(target = "subjectName", expression = "java(plannerEvent.getSubject() != null ? plannerEvent.getSubject().getName() : null)")
    @Mapping(target = "activityId", expression = "java(plannerEvent.getActivity() != null ? plannerEvent.getActivity().getId() : null)")
    @Mapping(target = "activityName", expression = "java(plannerEvent.getActivity() != null ? plannerEvent.getActivity().getTitle() : null)")
    public abstract PlannerEventResponseSummary toSummaryResponse(PlannerEvent plannerEvent);
}
