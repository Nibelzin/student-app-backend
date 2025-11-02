package com.studentapp.api.infra.adapters.in.web.mapper;

import com.studentapp.api.domain.model.Activity;
import com.studentapp.api.domain.model.PlannerEvent;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.infra.adapters.in.web.dto.plannerEvent.PlannerEventCreateRequest;
import com.studentapp.api.infra.adapters.in.web.dto.plannerEvent.PlannerEventResponseSummary;
import org.springframework.stereotype.Component;

@Component
public class PlannerEventDtoMapper {

    public PlannerEvent toDomain(PlannerEventCreateRequest request, User user, Subject subject, Activity activity){
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

    public PlannerEventResponseSummary toSummaryResponse(PlannerEvent plannerEvent){
        if(plannerEvent == null){
            return null;
        }

        PlannerEventResponseSummary response = new PlannerEventResponseSummary();
        response.setId(plannerEvent.getId());
        response.setTitle(plannerEvent.getTitle());
        response.setStartAt(plannerEvent.getStartAt());
        response.setEndAt(plannerEvent.getEndAt());
        response.setAllDay(plannerEvent.getAllDay());
        response.setRule(plannerEvent.getRule());
        response.setColor(plannerEvent.getColor());
        response.setUserId(plannerEvent.getUser().getId());
        response.setUserName(plannerEvent.getUser().getName());

        if(plannerEvent.getSubject() != null) {
            response.setSubjectId(plannerEvent.getSubject().getId());
            response.setSubjectName(plannerEvent.getSubject().getName());
        }

        if(plannerEvent.getActivity() != null) {
            response.setActivityId(plannerEvent.getActivity().getId());
            response.setActivityName(plannerEvent.getActivity().getTitle());
        }

        response.setCreatedAt(plannerEvent.getCreatedAt());
        response.setUpdatedAt(plannerEvent.getUpdatedAt());

        return response;

    }

}
