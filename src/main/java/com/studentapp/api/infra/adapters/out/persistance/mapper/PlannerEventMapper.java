package com.studentapp.api.infra.adapters.out.persistance.mapper;

import com.studentapp.api.domain.model.Activity;
import com.studentapp.api.domain.model.PlannerEvent;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.infra.adapters.out.persistance.entity.PlannerEventEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public abstract class PlannerEventMapper {

    @Autowired
    @Lazy
    protected UserMapper userMapper;

    @Autowired
    @Lazy
    protected SubjectMapper subjectMapper;

    @Autowired
    @Lazy
    protected ActivityMapper activityMapper;

    @Mapping(target = "user", expression = "java(userMapper.toEntity(plannerEvent.getUser()))")
    @Mapping(target = "subject", expression = "java(plannerEvent.getSubject() != null ? subjectMapper.toEntity(plannerEvent.getSubject()) : null)")
    @Mapping(target = "activity", expression = "java(plannerEvent.getActivity() != null ? activityMapper.toEntity(plannerEvent.getActivity()) : null)")
    public abstract PlannerEventEntity toEntity(PlannerEvent plannerEvent);

    public PlannerEvent toDomain(PlannerEventEntity entity) {
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

        Subject subjectDomain = entity.getSubject() != null ? subjectMapper.toDomain(entity.getSubject()) : null;
        Activity activityDomain = entity.getActivity() != null ? activityMapper.toDomain(entity.getActivity()) : null;

        return PlannerEvent.fromState(
                entity.getId(), entity.getTitle(), entity.getStartAt(), entity.getEndAt(),
                entity.getAllDay(), entity.getRule(), entity.getColor(),
                entity.getCreatedAt(), entity.getUpdatedAt(),
                userDomain, subjectDomain, activityDomain
        );
    }
}
