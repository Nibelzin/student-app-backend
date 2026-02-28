package com.studentapp.api.infra.adapters.out.persistance.mapper;

import com.studentapp.api.domain.model.Period;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    @Lazy
    protected PeriodMapper periodMapper;

    @Mapping(target = "periods", expression = "java(user.getPeriods().stream().map(periodMapper::toEntity).toList())")
    public abstract UserEntity toEntity(User user);

    public User toDomain(UserEntity entity) {
        if (entity == null) return null;

        List<Period> periods = entity.getPeriods() == null
                ? new ArrayList<>()
                : entity.getPeriods().stream().map(periodMapper::toDomain).toList();

        return User.fromState(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPasswordHash(),
                entity.getCourse(),
                entity.getCurrentSemester(),
                entity.getCurrentXp(),
                entity.getCurrentLevel(),
                entity.getCoins(),
                entity.getCurrentStreak(),
                entity.getLastActiveDate(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                periods,
                entity.getRole()
        );
    }
}