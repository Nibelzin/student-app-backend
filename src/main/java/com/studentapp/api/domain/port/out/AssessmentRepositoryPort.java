package com.studentapp.api.domain.port.out;

import com.studentapp.api.domain.model.assessment.Assessment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface AssessmentRepositoryPort {
    Assessment save(Assessment assessment);
    Optional<Assessment> findById(UUID id);
    Page<Assessment> findBySubjectId(UUID subjectId, Pageable pageable);
    Page<Assessment> findByUserId(UUID userId, Pageable pageable);
    void delete(UUID id);
}
