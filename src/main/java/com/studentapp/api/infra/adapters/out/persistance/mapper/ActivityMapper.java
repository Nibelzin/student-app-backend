package com.studentapp.api.infra.adapters.out.persistance.mapper;

import com.studentapp.api.domain.model.Activity;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.domain.port.out.ActivityRepositoryPort;
import com.studentapp.api.infra.adapters.out.persistance.entity.ActivityEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.SubjectEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ActivityMapper {

    private final SubjectMapper subjectMapper;

    public ActivityMapper(@Lazy SubjectMapper subjectMapper) {
        this.subjectMapper = subjectMapper;
    }

    public ActivityEntity toEntity(Activity activity){
        if (activity == null) {
            return null;
        }

        SubjectEntity subjectEntity = subjectMapper.toEntity(activity.getSubject());

        return new ActivityEntity(
                activity.getId(),
                activity.getTitle(),
                activity.getDescription(),
                activity.getDueDate(),
                activity.getCompleted(),
                activity.getType(), activity.getCreatedAt(),
                activity.getUpdatedAt(),
                subjectEntity);
    }

    public Activity toDomain(ActivityEntity entity){
        if (entity == null){
            return null;
        }

        Subject subjectDomain = subjectMapper.toDomain(entity.getSubject());

        return Activity.fromState(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getDueDate(),
                entity.getIsCompleted(),
                entity.getType(), entity.getCreatedAt(),
                entity.getUpdatedAt(),
                subjectDomain);
    }

}
