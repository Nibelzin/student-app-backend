package com.studentapp.api.infra.adapters.in.web.mapper;

import com.studentapp.api.domain.model.Period;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.infra.adapters.in.web.dto.period.PeriodCreateRequest;
import com.studentapp.api.infra.adapters.in.web.dto.period.PeriodResponse;
import com.studentapp.api.infra.adapters.in.web.dto.period.PeriodResponseSummary;
import org.springframework.stereotype.Component;

@Component
public class PeriodDtoMapper {

    public Period toDomain(PeriodCreateRequest request, User user){
        return Period.create(request.getName(), request.getStartDate(), request.getEndDate(), request.getIsCurrent(), user);
    }

    public PeriodResponseSummary toSummaryResponse(Period period) {
        if (period == null) {
            return null;
        }

        PeriodResponseSummary response = new PeriodResponseSummary();
        response.setId(period.getId());
        response.setName(period.getName());
        response.setStartDate(period.getStartDate());
        response.setEndDate(period.getEndDate());
        response.setIsCurrent(period.getCurrent());

        return response;
    }

    public PeriodResponse toResponse(Period period) {
        if (period == null) {
            return null;
        }

        PeriodResponse response = new PeriodResponse();
        response.setId(period.getId());
        response.setName(period.getName());
        response.setStartDate(period.getStartDate());
        response.setEndDate(period.getEndDate());
        response.setIsCurrent(period.getCurrent());
        response.setUserId(period.getUser().getId());

        return response;
    }

}
