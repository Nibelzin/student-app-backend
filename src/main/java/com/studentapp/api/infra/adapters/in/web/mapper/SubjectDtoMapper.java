package com.studentapp.api.infra.adapters.in.web.mapper;

import com.studentapp.api.domain.model.period.Period;
import com.studentapp.api.domain.model.subject.Subject;
import com.studentapp.api.domain.model.user.User;
import com.studentapp.api.infra.adapters.in.web.dto.subject.SubjectCreateRequest;
import com.studentapp.api.infra.adapters.in.web.dto.subject.SubjectResponse;
import org.springframework.stereotype.Component;

@Component
public class SubjectDtoMapper {

    public Subject toDomain(SubjectCreateRequest request, User user, Period period){
        return Subject.create(request.getName(), request.getProfessor(), request.getClassroom(), request.getColor(), request.getMaxAbsencesAllowed(), user, period);
    }

    public SubjectResponse toResponse(Subject subject) {
        if (subject == null) {
            return null;
        }

        SubjectResponse response = new SubjectResponse();
        response.setId(subject.getId());
        response.setName(subject.getName());
        response.setProfessor(subject.getProfessor());
        response.setClassroom(subject.getClassroom());
        response.setColor(subject.getColor());
        response.setMaxAbsencesAllowed(subject.getMaxAbsencesAllowed());
        response.setUserId(subject.getUser().getId());
        response.setPeriodId(subject.getPeriod().getId());
        response.setCreatedAt(subject.getCreatedAt());
        response.setPeriodName(subject.getPeriod().getName());

        return response;
    }

}
