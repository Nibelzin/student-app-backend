package com.studentapp.api.infra.adapters.out.persistance;

import com.studentapp.api.domain.model.Assessment;
import com.studentapp.api.domain.port.out.AssessmentRepositoryPort;
import com.studentapp.api.infra.adapters.out.persistance.entity.AssessmentEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.SubjectEntity;
import com.studentapp.api.infra.adapters.out.persistance.mapper.AssessmentMapper;
import com.studentapp.api.infra.adapters.out.persistance.repository.AssessmentJpaRepository;
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
public class AssessmentRepositoryAdapter implements AssessmentRepositoryPort {

    private final AssessmentJpaRepository assessmentJpaRepository;
    private final SubjectJpaRepository subjectJpaRepository;
    private final AssessmentMapper assessmentMapper;

    @Override
    public Assessment save(Assessment assessment) {
        AssessmentEntity entity = assessmentMapper.toEntity(assessment);
        return assessmentMapper.toDomain(assessmentJpaRepository.save(entity));
    }

    @Override
    public Optional<Assessment> findById(UUID id) {
        return assessmentJpaRepository.findById(id).map(assessmentMapper::toDomain);
    }

    @Override
    public Page<Assessment> findBySubjectId(UUID subjectId, Pageable pageable) {
        SubjectEntity subjectEntity = subjectJpaRepository.findById(subjectId).orElseThrow(
                () -> new ResourceNotFoundException("Matéria não encontrada.")
        );
        return assessmentJpaRepository.findBySubject(subjectEntity, pageable).map(assessmentMapper::toDomain);
    }

    @Override
    public void delete(UUID id) {
        assessmentJpaRepository.deleteById(id);
    }
}
