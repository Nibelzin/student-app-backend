package com.studentapp.api.infra.adapters.out.persistance.mapper;

import com.studentapp.api.domain.model.Period;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.infra.adapters.out.persistance.entity.PeriodEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public abstract class PeriodMapper {

    @Autowired
    @Lazy
    protected UserMapper userMapper;

    @Mapping(target = "isCurrent", source = "current")
    @Mapping(target = "user", expression = "java(userMapper.toEntity(period.getUser()))")
    public abstract PeriodEntity toEntity(Period period);

    public Period toDomain(PeriodEntity entity) {
        if (entity == null) return null;

        User userDomain = null;
        if (entity.getUser() != null) {
            UserEntity u = entity.getUser();
            userDomain = User.fromState(
                    u.getId(), u.getName(), u.getEmail(), u.getPasswordHash(),
                    u.getCourse(), u.getCurrentSemester(), u.getCurrentXp(),
                    u.getCurrentLevel(), u.getCoins(), u.getCurrentStreak(),
                    u.getLastActiveDate(), u.getCreatedAt(), u.getUpdatedAt(),
                    new ArrayList<>(), u.getRole()
            );
        }

        return Period.fromState(
                entity.getId(), entity.getName(), entity.getStartDate(), entity.getEndDate(),
                entity.getIsCurrent(), userDomain, entity.getCreatedAt(), entity.getUpdatedAt()
        );
    }
}
