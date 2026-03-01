package com.studentapp.api.infra.adapters.out.persistance;

import com.studentapp.api.domain.model.AbsenceLog;
import com.studentapp.api.domain.port.out.AbsenceLogRepositoryPort;
import com.studentapp.api.infra.adapters.out.persistance.entity.AbsenceLogEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.SubjectEntity;
import com.studentapp.api.infra.adapters.out.persistance.mapper.AbsenceLogMapper;
import com.studentapp.api.infra.adapters.out.persistance.repository.AbsenceLogJpaRepository;
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
public class AbsenceLogRepositoryAdapter implements AbsenceLogRepositoryPort {

    private final AbsenceLogJpaRepository absenceLogJpaRepository;
    private final SubjectJpaRepository subjectJpaRepository;
    private final AbsenceLogMapper absenceLogMapper;

    @Override
    public AbsenceLog save(AbsenceLog absenceLog) {
        AbsenceLogEntity entity = absenceLogMapper.toEntity(absenceLog);
        return absenceLogMapper.toDomain(absenceLogJpaRepository.save(entity));
    }

    @Override
    public AbsenceLog update(AbsenceLog absenceLog) {
        AbsenceLogEntity entity = absenceLogMapper.toEntity(absenceLog);
        return absenceLogMapper.toDomain(absenceLogJpaRepository.save(entity));
    }

    @Override
    public Optional<AbsenceLog> findById(UUID id) {
        return absenceLogJpaRepository.findById(id).map(absenceLogMapper::toDomain);
    }

    @Override
    public Page<AbsenceLog> findAll(Pageable pageable) {
        return absenceLogJpaRepository.findAll(pageable).map(absenceLogMapper::toDomain);
    }

    @Override
    public Page<AbsenceLog> findBySubjectId(UUID subjectId, Pageable pageable) {
        SubjectEntity subjectEntity = subjectJpaRepository.findById(subjectId).orElseThrow(
                () -> new ResourceNotFoundException("Matéria não encontrada.")
        );
        return absenceLogJpaRepository.findBySubject(subjectEntity, pageable).map(absenceLogMapper::toDomain);
    }

    @Override
    public void delete(UUID id) {
        absenceLogJpaRepository.deleteById(id);
    }
}
