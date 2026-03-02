package com.studentapp.api.domain.port.out;

import com.studentapp.api.domain.model.AbsenceLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface AbsenceLogRepositoryPort {
    AbsenceLog save(AbsenceLog absenceLog);
    AbsenceLog update(AbsenceLog absenceLog);
    Optional<AbsenceLog> findById(UUID id);
    Page<AbsenceLog> findAll(Pageable pageable);
    Page<AbsenceLog> findBySubjectId(UUID subjectId, Pageable pageable);
    long countBySubjectId(UUID subjectId);
    void delete(UUID id);
}