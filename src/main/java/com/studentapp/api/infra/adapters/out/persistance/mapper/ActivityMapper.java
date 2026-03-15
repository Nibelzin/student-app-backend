package com.studentapp.api.infra.adapters.out.persistance.mapper;

import com.studentapp.api.domain.model.activity.Activity;
import com.studentapp.api.domain.model.subject.Subject;
import com.studentapp.api.infra.adapters.out.persistance.entity.ActivityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring")
public abstract class ActivityMapper {

    @Autowired
    @Lazy
    protected SubjectMapper subjectMapper;

    @Mapping(target = "isCompleted", source = "completed")
    @Mapping(target = "xpAwarded", source = "xpAwarded")
    @Mapping(target = "subject", expression = "java(subjectMapper.toEntity(activity.getSubject()))")
    public abstract ActivityEntity toEntity(Activity activity);

    public Activity toDomain(ActivityEntity entity) {
        if (entity == null) return null;

        Subject subjectDomain = subjectMapper.toDomain(entity.getSubject());

        return Activity.fromState(
                entity.getId(), entity.getTitle(), entity.getDescription(),
                entity.getDueDate(), entity.getIsCompleted(),
                entity.getXpAwarded() != null ? entity.getXpAwarded() : false,
                entity.getType(), entity.getCreatedAt(), entity.getUpdatedAt(),
                subjectDomain, entity.getChecklist()
        );
    }
}
