package com.studentapp.api.infra.adapters.in.web.mapper;

import com.studentapp.api.domain.model.Period;
import com.studentapp.api.infra.adapters.in.web.dto.PeriodSummaryResponse;
import org.springframework.stereotype.Component;

@Component
public class PeriodDtoMapper {

    public PeriodSummaryResponse toSummaryResponse(Period period) {
        if (period == null) {
            return null;
        }

        PeriodSummaryResponse response = new PeriodSummaryResponse();
        response.setId(period.getId());
        response.setName(period.getName());
        response.setStartDate(period.getStartDate());
        response.setEndDate(period.getEndDate());
        response.setIsCurrent(period.getCurrent());

        return response;
    }

}
