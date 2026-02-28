package com.studentapp.api.infra.adapters.out.persistance.mapper;

import com.studentapp.api.domain.model.User;
import com.studentapp.api.domain.model.UserPreference;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserPreferenceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public abstract class UserPreferenceMapper {

    @Autowired
    @Lazy
    protected UserMapper userMapper;

    @Mapping(target = "user", expression = "java(userMapper.toEntity(userPreference.getUser()))")
    public abstract UserPreferenceEntity toEntity(UserPreference userPreference);

    public UserPreference toDomain(UserPreferenceEntity entity) {
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

        return UserPreference.fromState(
                entity.getId(), userDomain, entity.getTheme(), entity.getLanguage(),
                entity.getDashboardLayout(), entity.getSettings(),
                entity.getCreatedAt(), entity.getUpdatedAt()
        );
    }
}
