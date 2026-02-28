package com.studentapp.api.infra.adapters.out.persistance.mapper;

import com.studentapp.api.domain.model.Period;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.infra.adapters.out.persistance.entity.SubjectEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public abstract class SubjectMapper {

    @Autowired
    @Lazy
    protected UserMapper userMapper;

    @Autowired
    @Lazy
    protected PeriodMapper periodMapper;

    @Mapping(target = "user", expression = "java(userMapper.toEntity(subject.getUser()))")
    @Mapping(target = "period", expression = "java(periodMapper.toEntity(subject.getPeriod()))")
    public abstract SubjectEntity toEntity(Subject subject);

    public Subject toDomain(SubjectEntity entity) {
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

        Period periodDomain = periodMapper.toDomain(entity.getPeriod());

        return Subject.fromState(
                entity.getId(), entity.getName(), entity.getProfessor(),
                entity.getClassroom(), entity.getColor(), userDomain,
                periodDomain, entity.getCreatedAt(), entity.getUpdatedAt()
        );
    }
}
