package com.studentapp.api.infra.adapters.out.persistance;

import com.studentapp.api.domain.model.FocusSession;
import com.studentapp.api.domain.port.in.FocusSessionUseCase;
import com.studentapp.api.domain.port.out.FocusSessionRepositoryPort;
import com.studentapp.api.infra.adapters.out.persistance.entity.FocusSessionEntity;
import com.studentapp.api.infra.adapters.out.persistance.jpa.FocusSessionSpecification;
import com.studentapp.api.infra.adapters.out.persistance.mapper.FocusSessionMapper;
import com.studentapp.api.infra.adapters.out.persistance.repository.FocusSessionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FocusSessionRepositoryAdapter implements FocusSessionRepositoryPort {

    private final FocusSessionJpaRepository focusSessionJpaRepository;
    private final FocusSessionMapper focusSessionMapper;

    @Override
    public FocusSession save(FocusSession session) {
        FocusSessionEntity entity = focusSessionMapper.toEntity(session);
        FocusSessionEntity persisted = focusSessionJpaRepository.save(entity);
        return focusSessionMapper.toDomain(persisted);
    }

    @Override
    public Optional<FocusSession> findById(UUID id) {
        return focusSessionJpaRepository.findById(id).map(focusSessionMapper::toDomain);
    }

    @Override
    public Page<FocusSession> findByQuery(FocusSessionUseCase.FocusSessionQueryData query, Pageable pageable) {
        Specification<FocusSessionEntity> spec = FocusSessionSpecification.withQuery(query);
        return focusSessionJpaRepository.findAll(spec, pageable).map(focusSessionMapper::toDomain);
    }

    @Override
    public void delete(UUID id) {
        focusSessionJpaRepository.deleteById(id);
    }
}
