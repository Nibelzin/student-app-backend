package com.studentapp.api.infra.adapters.in.web.mapper;

import com.studentapp.api.domain.model.FocusSession;
import com.studentapp.api.infra.adapters.in.web.dto.focusSession.FocusSessionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FocusSessionDtoMapper {

    @Mapping(target = "isCompleted", source = "completed")
    @Mapping(target = "userId", expression = "java(focusSession.getUser().getId())")
    @Mapping(target = "subjectId", expression = "java(focusSession.getSubject() != null ? focusSession.getSubject().getId() : null)")
    @Mapping(target = "subjectName", expression = "java(focusSession.getSubject() != null ? focusSession.getSubject().getName() : null)")
    @Mapping(target = "activityId", expression = "java(focusSession.getActivity() != null ? focusSession.getActivity().getId() : null)")
    @Mapping(target = "activityName", expression = "java(focusSession.getActivity() != null ? focusSession.getActivity().getTitle() : null)")
    FocusSessionResponse toResponse(FocusSession focusSession);
}
