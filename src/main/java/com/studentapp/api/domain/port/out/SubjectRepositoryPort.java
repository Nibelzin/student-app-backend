package com.studentapp.api.domain.port.out;

import com.studentapp.api.domain.model.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface SubjectRepositoryPort {
    Subject save(Subject subject);
    Subject update(Subject subject);
    Optional<Subject> findById(UUID id);
    Page<Subject> findAll(Pageable pageable);
    Page<Subject> findByPeriodId(UUID periodId, Pageable pageable);
    Page<Subject> findByUserId(UUID userId, Pageable pageable);
    void delete(UUID id);
}
