package com.studentapp.api.infra.adapters.out.persistance.mapper;

import com.studentapp.api.domain.model.User;
import com.studentapp.api.infra.adapters.out.persistance.entity.PeriodEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PeriodMapper periodMapper;

    public UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }

        List<PeriodEntity> periodEntityList = user.getPeriods().stream().map(periodMapper::toEntity).toList();

        return new UserEntity(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getCourse(),
                user.getCurrentSemester(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                periodEntityList
        );

    }

    public User toDomain(UserEntity entity){
        if (entity == null) {
            return null;
        }

        return User.fromState(entity.getId(), entity.getName(), entity.getEmail(), entity.getPasswordHash(), entity.getCourse(), entity.getCurrentSemester(), entity.getCreatedAt(), entity.getUpdatedAt());
    }

}
