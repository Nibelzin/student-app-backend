package com.studentapp.api.infra.adapters.out.persistance.mapper;

import com.studentapp.api.domain.model.User;
import com.studentapp.api.domain.model.UserPreference;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserPreferenceEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserPreferenceMapper {

    private final UserMapper userMapper;

    public UserPreferenceMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserPreferenceEntity toEntity(UserPreference userPreference){
        if(userPreference == null){
            return null;
        }

        UserEntity userEntity = userMapper.toEntity(userPreference.getUser());

        return new UserPreferenceEntity(
                userPreference.getId(),
                userEntity,
                userPreference.getTheme(),
                userPreference.getLanguage(),
                userPreference.getDashboardLayout(),
                userPreference.getSettings(),
                userPreference.getCreatedAt(),
                userPreference.getUpdatedAt()
        );
    }

    public UserPreference toDomain(UserPreferenceEntity entity){
        if (entity == null) {
            return null;
        }

        User userDomain = null;
        if(entity.getUser() != null){
            UserEntity userEntity = entity.getUser();

            userDomain = User.fromState(
                    userEntity.getId(),
                    userEntity.getName(),
                    userEntity.getEmail(),
                    userEntity.getPasswordHash(),
                    userEntity.getCourse(),
                    userEntity.getCurrentSemester(),
                    userEntity.getCreatedAt(),
                    userEntity.getUpdatedAt(),
                    new ArrayList<>()
            );
        }

        return UserPreference.fromState(entity.getId(), userDomain, entity.getTheme(), entity.getLanguage(), entity.getDashboardLayout(), entity.getSettings(), entity.getCreatedAt(), entity.getUpdatedAt());
    }

}
