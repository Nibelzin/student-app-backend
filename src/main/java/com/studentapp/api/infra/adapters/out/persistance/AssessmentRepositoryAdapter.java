package com.studentapp.api.infra.adapters.out.persistance;

import com.studentapp.api.domain.model.assessment.Assessment;
import com.studentapp.api.domain.port.in.AssessmentUseCase;
import com.studentapp.api.domain.port.out.AssessmentRepositoryPort;
import com.studentapp.api.infra.adapters.out.persistance.entity.AssessmentEntity;
import com.studentapp.api.infra.adapters.out.persistance.jpa.AssessmentSpecification;
import com.studentapp.api.infra.adapters.out.persistance.mapper.AssessmentMapper;
import com.studentapp.api.infra.adapters.out.persistance.repository.AssessmentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AssessmentRepositoryAdapter implements AssessmentRepositoryPort {

    private final AssessmentJpaRepository assessmentJpaRepository;
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
    public Page<Assessment> findByQuery(AssessmentUseCase.AssessmentQueryData queryData, Pageable pageable) {
        Specification<AssessmentEntity> spec = AssessmentSpecification.byCriteria(queryData);
        return assessmentJpaRepository.findAll(spec, pageable).map(assessmentMapper::toDomain);
    }

    @Override
    public void delete(UUID id) {
        assessmentJpaRepository.deleteById(id);
    }
}
