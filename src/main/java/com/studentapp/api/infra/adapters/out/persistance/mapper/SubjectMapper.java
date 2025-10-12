package com.studentapp.api.infra.adapters.out.persistance.mapper;

import com.studentapp.api.domain.model.Period;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.infra.adapters.out.persistance.entity.PeriodEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.SubjectEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SubjectMapper {

    private final UserMapper userMapper;
    private final PeriodMapper periodMapper;

    public SubjectMapper(UserMapper userMapper, PeriodMapper periodMapper) {
        this.userMapper = userMapper;
        this.periodMapper = periodMapper;
    }

    public SubjectEntity toEntity(Subject subject){
        if(subject == null){
            return null;
        }

        UserEntity userEntity = userMapper.toEntity(subject.getUser());
        PeriodEntity periodEntity = periodMapper.toEntity(subject.getPeriod());

        return new SubjectEntity(
                subject.getId(),
                subject.getName(),
                subject.getProfessor(),
                subject.getClassroom(),
                subject.getColor(),
                subject.getCreatedAt(),
                subject.getUpdatedAt(),
                userEntity,
                periodEntity
        );
    }

    public Subject toDomain(SubjectEntity entity){
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

        Period periodDomain = periodMapper.toDomain(entity.getPeriod());

        return Subject.fromState(entity.getId(), entity.getName(), entity.getProfessor(), entity.getClassroom(), entity.getColor(), userDomain, periodDomain, entity.getCreatedAt(), entity.getUpdatedAt());
    }

}
