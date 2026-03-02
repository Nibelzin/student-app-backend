package com.studentapp.api.infra.adapters.out.persistance.mapper;

import com.studentapp.api.domain.model.Notification;
import com.studentapp.api.domain.model.NotificationType;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.infra.adapters.out.persistance.entity.NotificationEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public abstract class NotificationMapper {

    @Autowired
    @Lazy
    protected UserMapper userMapper;

    @Mapping(target = "type", expression = "java(notification.getType().name())")
    @Mapping(target = "user", expression = "java(userMapper.toEntity(notification.getUser()))")
    public abstract NotificationEntity toEntity(Notification notification);

    public Notification toDomain(NotificationEntity entity) {
        if (entity == null) return null;

        User user = null;
        if (entity.getUser() != null) {
            UserEntity u = entity.getUser();
            user = User.fromState(
                    u.getId(), u.getName(), u.getEmail(), u.getPasswordHash(),
                    u.getCourse(), u.getCurrentSemester(), u.getCurrentXp(),
                    u.getCurrentLevel(), u.getCoins(), u.getCurrentStreak(),
                    u.getLastActiveDate(), u.getCreatedAt(), u.getUpdatedAt(),
                    new ArrayList<>(), u.getRole()
            );
        }

        return Notification.fromState(
                entity.getId(),
                NotificationType.valueOf(entity.getType()),
                entity.getMessage(),
                entity.isRead(),
                entity.getReferenceId(),
                entity.getCreatedAt(),
                user
        );
    }
}
