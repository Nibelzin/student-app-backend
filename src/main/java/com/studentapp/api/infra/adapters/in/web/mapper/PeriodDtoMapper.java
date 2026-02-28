package com.studentapp.api.infra.adapters.in.web.mapper;

import com.studentapp.api.domain.model.Period;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.infra.adapters.in.web.dto.period.PeriodCreateRequest;
import com.studentapp.api.infra.adapters.in.web.dto.period.PeriodResponse;
import com.studentapp.api.infra.adapters.in.web.dto.period.PeriodResponseSummary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class PeriodDtoMapper {

    public Period toDomain(PeriodCreateRequest request, User user) {
        return Period.create(request.getName(), request.getStartDate(), request.getEndDate(), request.getIsCurrent(), user);
    }

    @Mapping(target = "isCurrent", source = "current")
    public abstract PeriodResponseSummary toSummaryResponse(Period period);

    @Mapping(target = "isCurrent", source = "current")
    @Mapping(target = "userId", expression = "java(period.getUser().getId())")
    public abstract PeriodResponse toResponse(Period period);
}
