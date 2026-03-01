package com.studentapp.api.infra.adapters.out.persistance;

import com.studentapp.api.domain.model.ClassSchedule;
import com.studentapp.api.domain.port.out.ClassScheduleRepositoryPort;
import com.studentapp.api.infra.adapters.out.persistance.entity.ClassScheduleEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.SubjectEntity;
import com.studentapp.api.infra.adapters.out.persistance.mapper.ClassScheduleMapper;
import com.studentapp.api.infra.adapters.out.persistance.repository.ClassScheduleJpaRepository;
import com.studentapp.api.infra.adapters.out.persistance.repository.SubjectJpaRepository;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ClassScheduleRepositoryAdapter implements ClassScheduleRepositoryPort {

    private final ClassScheduleJpaRepository classScheduleJpaRepository;
    private final SubjectJpaRepository subjectJpaRepository;
    private final ClassScheduleMapper classScheduleMapper;

    @Override
    public ClassSchedule save(ClassSchedule classSchedule) {
        ClassScheduleEntity entity = classScheduleMapper.toEntity(classSchedule);
        return classScheduleMapper.toDomain(classScheduleJpaRepository.save(entity));
    }

    @Override
    public Optional<ClassSchedule> findById(UUID id) {
        return classScheduleJpaRepository.findById(id).map(classScheduleMapper::toDomain);
    }

    @Override
    public Page<ClassSchedule> findBySubjectId(UUID subjectId, Pageable pageable) {
        SubjectEntity subjectEntity = subjectJpaRepository.findById(subjectId).orElseThrow(
                () -> new ResourceNotFoundException("Matéria não encontrada.")
        );
        return classScheduleJpaRepository.findBySubject(subjectEntity, pageable).map(classScheduleMapper::toDomain);
    }

    @Override
    public void delete(UUID id) {
        classScheduleJpaRepository.deleteById(id);
    }
}
