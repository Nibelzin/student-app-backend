package com.studentapp.api.infra.adapters.out.persistance.mapper;

import com.studentapp.api.domain.model.Activity;
import com.studentapp.api.domain.model.FocusSession;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.infra.adapters.out.persistance.entity.FocusSessionEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public abstract class FocusSessionMapper {

    @Autowired
    @Lazy
    protected UserMapper userMapper;

    @Autowired
    @Lazy
    protected SubjectMapper subjectMapper;

    @Autowired
    @Lazy
    protected ActivityMapper activityMapper;

    @Mapping(target = "isCompleted", source = "completed")
    @Mapping(target = "user", expression = "java(userMapper.toEntity(focusSession.getUser()))")
    @Mapping(target = "subject", expression = "java(focusSession.getSubject() != null ? subjectMapper.toEntity(focusSession.getSubject()) : null)")
    @Mapping(target = "activity", expression = "java(focusSession.getActivity() != null ? activityMapper.toEntity(focusSession.getActivity()) : null)")
    public abstract FocusSessionEntity toEntity(FocusSession focusSession);

    public FocusSession toDomain(FocusSessionEntity entity) {
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

        Subject subject = entity.getSubject() != null ? subjectMapper.toDomain(entity.getSubject()) : null;
        Activity activity = entity.getActivity() != null ? activityMapper.toDomain(entity.getActivity()) : null;

        return FocusSession.fromState(
                entity.getId(),
                entity.getDurationSeconds(),
                Boolean.TRUE.equals(entity.getIsCompleted()),
                entity.getXpEarned(),
                entity.getCreatedAt(),
                user,
                subject,
                activity
        );
    }
}
