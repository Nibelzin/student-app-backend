package com.studentapp.api.infra.adapters.out.persistance.mapper;

import com.studentapp.api.domain.model.Activity;
import com.studentapp.api.domain.model.PlannerEvent;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.infra.adapters.out.persistance.entity.ActivityEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.PlannerEventEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.SubjectEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PlannerEventMapper {
    
    private final UserMapper userMapper;
    private final SubjectMapper subjectMapper;
    private final ActivityMapper activityMapper;
    
    public PlannerEventMapper(@Lazy UserMapper userMapper,@Lazy  SubjectMapper subjectMapper,@Lazy  ActivityMapper activityMapper) {
        this.userMapper = userMapper;
        this.subjectMapper = subjectMapper;
        this.activityMapper = activityMapper;
    }
    
    public PlannerEventEntity toEntity(PlannerEvent plannerEvent){
        if(plannerEvent == null){
            return null;
        }
        
        UserEntity userEntity = userMapper.toEntity(plannerEvent.getUser());
        SubjectEntity subjectEntity = null;
        if(plannerEvent.getSubject() != null){
            subjectEntity = subjectMapper.toEntity(plannerEvent.getSubject());
        }
        
        ActivityEntity activityEntity = null;
        if(plannerEvent.getActivity() != null){
            activityEntity = activityMapper.toEntity(plannerEvent.getActivity());
        }

        return new PlannerEventEntity(
            plannerEvent.getId(),
            plannerEvent.getTitle(),
            plannerEvent.getStartAt(),
            plannerEvent.getEndAt(),
            plannerEvent.getAllDay(),
            plannerEvent.getRule(),
            plannerEvent.getColor(),
            plannerEvent.getCreatedAt(),
            plannerEvent.getUpdatedAt(),
            userEntity,
            subjectEntity,
            activityEntity
        );
    }

    public PlannerEvent toDomain(PlannerEventEntity entity){
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

        Subject subjectDomain = null;
        if(entity.getSubject() != null){
            subjectDomain = subjectMapper.toDomain(entity.getSubject());
        }

        Activity activityDomain = null;
        if (entity.getActivity() != null){
            activityDomain = activityMapper.toDomain(entity.getActivity());
        }

        return PlannerEvent.fromState(
                entity.getId(),
                entity.getTitle(),
                entity.getStartAt(),
                entity.getEndAt(),
                entity.getAllDay(),
                entity.getRule(),
                entity.getColor(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                userDomain,
                subjectDomain,
                activityDomain);
    }
}
