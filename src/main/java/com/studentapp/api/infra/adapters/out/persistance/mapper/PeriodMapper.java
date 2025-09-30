package com.studentapp.api.infra.adapters.out.persistance.mapper;

import com.studentapp.api.domain.model.Period;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.infra.adapters.out.persistance.entity.PeriodEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class PeriodMapper {

    private final UserMapper userMapper;

    public PeriodMapper(@Lazy UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public PeriodEntity toEntity(Period period) {
        if (period == null) {
            return null;
        }

        UserEntity userEntity = userMapper.toEntity(period.getUser());

        return new PeriodEntity(
                period.getId(),
                period.getName(),
                period.getStartDate(),
                period.getEndDate(),
                period.getCurrent(),
                period.getCreatedAt(),
                period.getUpdatedAt(),
                userEntity
        );
    }

    public Period toDomain(PeriodEntity entity){
        if (entity == null) {
            return null;
        }

        User userDomain = userMapper.toDomain(entity.getUser());

        return Period.fromState(entity.getId(), entity.getName(), entity.getStartDate(), entity.getEndDate(), entity.getIsCurrent(), userDomain, entity.getCreatedAt(), entity.getUpdatedAt());
    }

}
